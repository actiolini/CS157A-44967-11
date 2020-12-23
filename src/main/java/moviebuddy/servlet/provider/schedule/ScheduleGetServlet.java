package moviebuddy.servlet.provider.schedule;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.util.List;

import moviebuddy.dao.TheatreDAO;
import moviebuddy.dao.MovieDAO;
import moviebuddy.dao.ScheduleDAO;
import moviebuddy.model.Theatre;
import moviebuddy.model.Movie;
import moviebuddy.model.Schedule;
import moviebuddy.util.Validation;
import moviebuddy.util.S;

@WebServlet("/ScheduleGet")
public class ScheduleGetServlet extends HttpServlet {
    private static final long serialVersionUID = 6599031675901730118L;

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
            Object role = session.getAttribute(S.ROLE);
            // Check authorized access as admin and manager
            if (role != null && (role.equals(S.ADMIN) || role.equals(S.MANAGER))) {

                // Retrieve current theatre id
                String theatreId = "";
                Object theatreIdObj = session.getAttribute(S.SCHEDULE_THEATRE_ID);
                if (theatreIdObj != null) {
                    theatreId = theatreIdObj.toString();
                }

                // Retrieve current movie id
                String movieId = "";
                Object movieIdObj = session.getAttribute(S.SCHEDULE_MOVIE_ID);
                if (movieIdObj != null) {
                    movieId = movieIdObj.toString();
                }

                // Retrieve list of rooms
                session.setAttribute(S.ROOM_THEATRE_ID, theatreId);
                RequestDispatcher rd = request.getRequestDispatcher("RoomGet");
                rd.include(request, response);

                // Retrieve list of schedules
                List<Schedule> schedules = scheduleDAO.listScheduleByMovie(theatreId, movieId);
                session.setAttribute(S.SCHEDULE_LIST, schedules);
            } else {
                // Redirect to Home page for unauthorized access
                response.sendRedirect(S.HOME_PAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(S.ERROR_PAGE);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute(S.ROLE);
            // Check authorized access as admin and manager
            if (role != null && (role.equals(S.ADMIN) || role.equals(S.MANAGER))) {
                String theatreId = "";

                // Retrieve theatre id as admin
                if (role.equals(S.ADMIN)) {
                    // Initiate selected theatre id
                    if (session.getAttribute(S.SELECTED_THEATRE_ID) == null) {
                        List<Theatre> theatres = theatreDAO.listTheatres();
                        if (!theatres.isEmpty()) {
                            session.setAttribute(S.SELECTED_THEATRE_ID, theatres.get(0).getId());
                        } else {
                            session.setAttribute(S.SELECTED_THEATRE_ID, "");
                        }
                    }
                    theatreId = session.getAttribute(S.SELECTED_THEATRE_ID).toString();

                    // Retrieve current selected theatre id
                    String selectTheatreId = request.getParameter("selectTheatreOption");
                    if (selectTheatreId != null) {
                        theatreId = Validation.sanitize(selectTheatreId);
                        session.setAttribute(S.SELECTED_THEATRE_ID, theatreId);
                    }
                }

                // Retrieve theatre id as manager
                if (role.equals(S.MANAGER)) {
                    theatreId = "";
                    Object theatreIdObj = session.getAttribute(S.EMPLOY_THEATRE_ID);
                    if (theatreIdObj != null) {
                        theatreId = theatreIdObj.toString();
                    }
                }

                // Set current theatre information in session
                Theatre theatre = theatreDAO.getTheatreById(theatreId);
                session.setAttribute(S.SCHEDULE_THEATRE_ID, theatreId);
                session.setAttribute(S.SCHEDULE_THEATRE_NAME, theatre.getTheatreName());

                // Set current movie information in session
                String selectMovieId = request.getParameter("movieId");
                if(selectMovieId != null){
                    String movieId = Validation.sanitize(selectMovieId);
                    Movie movie = movieDAO.getMovieById(movieId);
                    session.setAttribute(S.SCHEDULE_MOVIE_ID, movieId);
                    session.setAttribute(S.SCHEDULE_MOVIE_TITLE, movie.getTitle());
                }

                // Redirect to Manage Schedule page
                response.sendRedirect(S.MANAGE_SCHEDULE_PAGE);
            } else {
                // Redirect to Home page for unauthorized access
                response.sendRedirect(S.HOME_PAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(S.ERROR_PAGE);
        }
    }
}