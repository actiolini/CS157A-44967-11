package moviebuddy.servlet.provider.movie;

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

@WebServlet("/MovieUpload")
@MultipartConfig
public class MovieUploadSevlet extends HttpServlet {
    private static final long serialVersionUID = 3223494789974884818L;

    private static final String TITLE = "movieTitleUpload";
    private static final String RELEASE_DATE = "movieReleaseDateUpload";
    private static final String DURATION = "movieDurationUpload";
    private static final String TRAILER = "movieTrailerUpload";
    private static final String DESCRIPTION = "movieDescriptionUpload";
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
                String title = Validation.sanitize(request.getParameter("title"));
                String releaseDate = Validation.sanitize(request.getParameter("releaseDate"));
                String duration = Validation.sanitize(request.getParameter("duration"));
                String trailer = Validation.sanitize(request.getParameter("trailer"));
                Part partPoster = request.getPart("poster");
                InputStream streamPoster = partPoster.getInputStream();
                long posterSize = partPoster.getSize();
                String description = Validation.sanitize(request.getParameter("description"));
                String errorMessage = Validation.validateDate(releaseDate);
                if (errorMessage.isEmpty()) {
                    errorMessage = Validation.validateNumber(duration);
                }
                if (errorMessage.isEmpty()) {
                    errorMessage = movieDAO.uploadMovie(title, releaseDate, duration, trailer, streamPoster, posterSize,
                            description);
                }
                if (errorMessage.isEmpty()) {
                    response.sendRedirect("managemovie.jsp");
                } else {
                    session.setAttribute("errorMessage", errorMessage);
                    session.setAttribute(TITLE, title);
                    session.setAttribute(RELEASE_DATE, releaseDate);
                    session.setAttribute(DURATION, duration);
                    session.setAttribute(TRAILER, trailer);
                    session.setAttribute(DESCRIPTION, description);
                    response.sendRedirect("movieupload.jsp");
                }
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}
