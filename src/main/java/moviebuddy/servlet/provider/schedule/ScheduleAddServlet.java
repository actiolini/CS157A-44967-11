package moviebuddy.servlet.provider.schedule;

import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import moviebuddy.dao.MovieDAO;
import moviebuddy.dao.ScheduleDAO;
import moviebuddy.model.Movie;
import moviebuddy.model.Schedule;
import moviebuddy.model.ShowTime;
import moviebuddy.util.Validation;
import moviebuddy.util.S;

@WebServlet("/ScheduleAdd")
public class ScheduleAddServlet extends HttpServlet {
    private static final long serialVersionUID = -9166508971254120994L;

    private MovieDAO movieDAO;
    private ScheduleDAO scheduleDAO;

    public void init() {
        movieDAO = new MovieDAO();
        scheduleDAO = new ScheduleDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Object role = session.getAttribute(S.ROLE);
            if (role != null && role.equals(S.ADMIN)) {
                String theatreId = "";
                Object theatreIdObj = session.getAttribute(S.SCHEDULE_THEATRE_ID);
                if (theatreIdObj != null) {
                    theatreId = theatreIdObj.toString();
                }
                String movieId = "";
                Object movieIdObj = session.getAttribute(S.SCHEDULE_MOVIE_ID);
                if (movieIdObj != null) {
                    movieId = movieIdObj.toString();
                }
                // Sanitize user inputs
                String showDate = Validation.sanitize(request.getParameter("showDate"));
                String startTime = Validation.sanitize(request.getParameter("startTime"));
                String roomNumber = Validation.sanitize(request.getParameter("roomNumber"));

                // Validate user inputs
                String errorMessage = Validation.validateScheduleForm(showDate, startTime, roomNumber);
                //check for valid room number

                Movie movie = movieDAO.getMovieById(movieId);
                // Check for time conflict
                // if (errorMessage.isEmpty()) {
                // LocalTime startTime = LocalTime.parse(showTime);
                // LocalTime endTime = startTime.plusMinutes(movie.getDuration());
                // ShowTime interval = new ShowTime(startTime, endTime);
                // List<Schedule> schedule = scheduleDAO.listScheduleByMovieDate(theatreId,
                // movieId, showDate);
                // errorMessage = Validation.checkScheduleConflict(schedule, interval);
                // }

                // Check for space conflict
                // if (errorMessage.isEmpty()) {
                // LocalTime startTime = LocalTime.parse(showTime);
                // LocalTime endTime = startTime.plusMinutes(movie.getDuration());
                // ShowTime interval = new ShowTime(startTime, endTime);
                // List<Schedule> schedule = scheduleDAO.listScheduleByRoomDate(theatreId,
                // roomNumber, showDate);
                // errorMessage = Validation.checkScheduleConflict(schedule, interval);
                // }

                if (errorMessage.isEmpty()) {
                    LocalTime endTime = LocalTime.parse(startTime).plusMinutes(movie.getDuration());
                    errorMessage = scheduleDAO.addSchedule(theatreId, roomNumber, movieId, showDate, startTime,
                            endTime.toString());
                }

                if (!errorMessage.isEmpty()) {
                    session.setAttribute(S.SCHEDULE_SHOW_DATE_CREATE, showDate);
                    session.setAttribute(S.SCHEDULE_START_TIME_CREATE, startTime);
                    session.setAttribute(S.SCHEDULE_ROOM_NUMBER_CREATE, roomNumber);
                    session.setAttribute(S.ERROR_MESSAGE, errorMessage);
                }
                response.sendRedirect(S.MANAGE_SCHEDULE_PAGE);
            } else {
                response.sendRedirect(S.HOME_PAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(S.ERROR_PAGE);
        }
    }
}
