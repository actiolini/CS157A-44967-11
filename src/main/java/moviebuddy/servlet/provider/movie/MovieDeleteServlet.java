package moviebuddy.servlet.provider.movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import moviebuddy.dao.MovieDAO;
import moviebuddy.util.Validation;

@WebServlet("/MovieDelete")
public class MovieDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = -2683675903760366416L;
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
                String movieId = Validation.sanitize(request.getParameter("movieId"));
                String errorMessage = movieDAO.deleteMovie(movieId);
                if (!errorMessage.isEmpty()) {
                    session.setAttribute("errorMessage", errorMessage);
                }
                response.sendRedirect("managemovie.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
