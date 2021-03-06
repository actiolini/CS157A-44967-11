package moviebuddy.servlet.provider.movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import moviebuddy.dao.MovieDAO;
import moviebuddy.model.Movie;
import moviebuddy.util.Validation;

@WebServlet("/MovieLoadEdit")
public class MovieLoadEditServlet extends HttpServlet {
    private static final long serialVersionUID = 4761952928243992449L;

    private static final String MOVIE_ID = "movieIdEdit";
    private static final String TITLE = "movieTitleEdit";
    private static final String RELEASE_DATE = "movieReleaseDateEdit";
    private static final String DURATION = "movieDurationEdit";
    private static final String TRAILER = "movieTrailerEdit";
    private static final String DESCRIPTION = "movieDescriptionEdit";

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
                Movie movie = movieDAO.getMovieById(movieId);
                if (movie != null) {
                    session.setAttribute(MOVIE_ID, movieId);
                    session.setAttribute(TITLE, movie.getTitle());
                    session.setAttribute(RELEASE_DATE, movie.getReleaseDate());
                    session.setAttribute(DURATION, movie.getDuration());
                    session.setAttribute(TRAILER, movie.getTrailer());
                    session.setAttribute(DESCRIPTION, movie.getDescription());
                    response.sendRedirect("movieedit.jsp");
                } else {
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
