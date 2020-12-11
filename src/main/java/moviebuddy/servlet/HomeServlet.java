package moviebuddy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import moviebuddy.dao.ScheduleDAO;
import moviebuddy.model.Movie;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 156345443434387L;
    private ScheduleDAO movieScheduleDAO;

    public void init() {
        movieScheduleDAO = new ScheduleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // List<Movie> movies = movieScheduleDAO.getMovieSchedule();
            // request.setAttribute("movies", movies);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
