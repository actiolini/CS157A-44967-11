package moviebuddy.servlet.provider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import moviebuddy.dao.MovieDAO;

@WebServlet("/DeleteMovie")
public class DeleteMovieServlet extends HttpServlet {
    private static final long serialVersionUID = -2683675903760366416L;
    private MovieDAO movieDAO;

    public void init() {
        movieDAO = new MovieDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String movieId = request.getParameter("movieId");
            movieDAO.deleteMovieInfo(movieId);
            response.sendRedirect("./managemovie.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
