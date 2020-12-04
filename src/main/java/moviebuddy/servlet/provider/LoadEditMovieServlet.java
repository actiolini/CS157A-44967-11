package moviebuddy.servlet.provider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import moviebuddy.dao.MovieDAO;
import moviebuddy.model.Movie;

@WebServlet("/LoadMovie")
public class LoadEditMovieServlet extends HttpServlet {
    private static final long serialVersionUID = 4761952928243992449L;
    private MovieDAO movieDAO;

    public void init() {
        movieDAO = new MovieDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            Movie movie = movieDAO.getMovieInfo(movieId);
            HttpSession session = request.getSession();
            session.setAttribute("editMovieId", movieId);
            session.setAttribute("editTitle", movie.getTitle());
            session.setAttribute("editReleaseDate", movie.getReleaseDate());
            session.setAttribute("editDuration", movie.getDuration());
            session.setAttribute("editTrailer", movie.getTrailer());
            session.setAttribute("editDescription", movie.getDescription());
            response.sendRedirect("./movieedit.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
