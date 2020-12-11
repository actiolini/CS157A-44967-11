package moviebuddy.servlet.provider.schedule;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.dao.MovieDAO;
import moviebuddy.dao.ScheduleDAO;
import moviebuddy.model.Theatre;
import moviebuddy.model.Movie;
import moviebuddy.model.Schedule;
import moviebuddy.util.Validation;

@WebServlet("/ScheduleGet")
public class ScheduleGetServlet extends HttpServlet {
    private static final long serialVersionUID = 6599031675901730118L;

    private static final String SELECTED_THEATRE_ID = "selectTheatreId";
    private static final String THEATRE_ID = "scheduleTheatreId";
    private static final String THEATRE_NAME = "scheduleTheatreName";
    private static final String MOVIE_ID = "scheduleMovieId";
    private static final String MOVIE_TITLE = "scheduleMovieTitle";
    private static final String SCHEDULES = "scheduleList";

    private TheatreDAO theatreDAO;
    private MovieDAO movieDAO;
    private ScheduleDAO scheduleDAO;

    public void init() {
        theatreDAO = new TheatreDAO();
        movieDAO = new MovieDAO();
        scheduleDAO = new ScheduleDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && (role.equals("admin") || role.equals("manager"))) {
                String theatreId = session.getAttribute(THEATRE_ID).toString();
                String movieId = session.getAttribute(MOVIE_ID).toString();
                List<Schedule> schedules = scheduleDAO.listScheduleByMovie(theatreId, movieId);
                request.setAttribute(SCHEDULES, schedules);
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute("role");
            if (role != null && (role.equals("admin") || role.equals("manager"))) {
                String theatreId = "";
                if (role.equals("admin")) {
                    if (session.getAttribute(SELECTED_THEATRE_ID) == null) {
                        List<Theatre> theatres = theatreDAO.listTheatres();
                        if (!theatres.isEmpty()) {
                            session.setAttribute(SELECTED_THEATRE_ID, theatres.get(0).getId());
                        } else {
                            session.setAttribute(SELECTED_THEATRE_ID, "");
                        }
                    }
                    theatreId = session.getAttribute(SELECTED_THEATRE_ID).toString();
                    if (request.getParameter("selectTheatreOption") != null) {
                        theatreId = Validation.sanitize(request.getParameter("selectTheatreOption"));
                        session.setAttribute(SELECTED_THEATRE_ID, theatreId);
                    }
                }
                if (role.equals("manager")) {
                    theatreId = session.getAttribute("employTheatreId").toString();
                }
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                session.setAttribute(THEATRE_ID, theatreId);
                session.setAttribute(THEATRE_NAME, theatre.getTheatreName());
                if (request.getParameter("movieId") != null) {
                    String movieId = Validation.sanitize(request.getParameter("movieId"));
                    Movie movie = movieDAO.getMovieById(movieId);
                    session.setAttribute(MOVIE_ID, movieId);
                    session.setAttribute(MOVIE_TITLE, movie.getTitle());
                }
                response.sendRedirect("manageschedule.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } catch (Exception e) {
            response.sendRedirect("error.jsp");
            e.printStackTrace();
        }
    }
}