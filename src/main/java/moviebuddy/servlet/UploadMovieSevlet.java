package moviebuddy.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

import moviebuddy.dao.MovieDAO;
import moviebuddy.util.Validation;

@WebServlet("/UploadMovie")
@MultipartConfig
public class UploadMovieSevlet extends HttpServlet {
    private static final long serialVersionUID = 3223494789974884818L;
    private MovieDAO movieDAO;

    public void init() {
        movieDAO = new MovieDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String title = request.getParameter("title");
            String releaseDate = request.getParameter("releaseDate");
            String duration = request.getParameter("duration");
            String trailer = request.getParameter("trailer");
            Part partPoster = request.getPart("poster");
            InputStream streamPoster = partPoster.getInputStream();
            long posterSize = partPoster.getSize();
            String description = request.getParameter("description");
            String errorMessage = Validation.validateNumber(duration);
            if (errorMessage.isEmpty()) {
                errorMessage = movieDAO.upload(title, releaseDate, duration, trailer, streamPoster, posterSize,
                        description);
            }
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", errorMessage);
            if (errorMessage.isEmpty()) {
                session.setAttribute("goodMessage", "Upload Successfully");
            }
            response.sendRedirect("movieupload.jsp");
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
