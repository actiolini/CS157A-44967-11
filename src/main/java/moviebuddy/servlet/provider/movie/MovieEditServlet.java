package moviebuddy.servlet.provider.movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

import moviebuddy.dao.MovieDAO;
import moviebuddy.util.Validation;

@WebServlet("/MovieEdit")
@MultipartConfig
public class MovieEditServlet extends HttpServlet {
    private static final long serialVersionUID = 6293849533123808650L;
    private MovieDAO movieDAO;

    public void init() {
        movieDAO = new MovieDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                if (request.getParameter("action").equals("save")) {
                    String movieId = Validation.sanitize(request.getParameter("movieId"));
                    String title = Validation.sanitize(request.getParameter("title"));
                    String releaseDate = Validation.sanitize(request.getParameter("releaseDate"));
                    String duration = Validation.sanitize(request.getParameter("duration"));
                    String trailer = Validation.sanitize(request.getParameter("trailer"));
                    Part partPoster = request.getPart("poster");
                    InputStream streamPoster = partPoster.getInputStream();
                    long posterSize = partPoster.getSize();
                    String description = Validation.sanitize(request.getParameter("description"));
                    String errorMessage = movieDAO.updateMovie(movieId, title, releaseDate, duration, trailer,
                            streamPoster, posterSize, description);
                    if (errorMessage.isEmpty()) {
                        response.sendRedirect("managemovie.jsp");
                    } else {
                        session.setAttribute("errorMessage", errorMessage);
                        response.sendRedirect("movieedit.jsp");
                    }
                }
                if (request.getParameter("action").equals("cancel")) {
                    response.sendRedirect("managemovie.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
