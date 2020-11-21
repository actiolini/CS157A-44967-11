package moviebuddy.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import moviebuddy.dao.MovieScheduleDAO;
import moviebuddy.model.Movie;

@WebServlet("/Home")
public class HomeSevlet extends HttpServlet {
    private static final long serialVersionUID = 156345443434387L;
    private MovieScheduleDAO movieScheduleDao;

    public void init() {
        movieScheduleDao = new MovieScheduleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Movie> movies = movieScheduleDao.getMovieSchedule();
            request.setAttribute("movies", movies);
            // RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
            // rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
