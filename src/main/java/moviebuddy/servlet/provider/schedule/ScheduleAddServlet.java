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

@WebServlet("/ScheduleAdd")
public class ScheduleAddServlet extends HttpServlet {
    private static final long serialVersionUID = -9166508971254120994L;

    private static final String THEATRE_ID = "scheduleTheatreId";
    private static final String MOVIE_ID = "scheduleMovieId";
    private static final String SHOW_DATE = "scheduleShowDateUpload";
    private static final String START_TIME = "scheduleShowTimeUpload";
    private static final String ROOM_NUMBER = "scheduleRoomNumberUpload";

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
            Object role = session.getAttribute("role");
            if (role != null && role.equals("admin")) {
                String theatreId = session.getAttribute(THEATRE_ID).toString();
                String movieId = session.getAttribute(MOVIE_ID).toString();
                String showDate = Validation.sanitize(request.getParameter("showDate"));
                String showTime = Validation.sanitize(request.getParameter("showTime"));
                String roomNumber = Validation.sanitize(request.getParameter("roomNumber"));
                String errorMessage = Validation.validateDate(showDate);
                if (errorMessage.isEmpty()) {
                    errorMessage = Validation.validateTime(showTime);
                }
                if (errorMessage.isEmpty()) {
                    errorMessage = Validation.validateNumber(roomNumber);
                }
                Movie movie = movieDAO.getMovieById(movieId);
                // Check for time conflict
                if (errorMessage.isEmpty()) {
                    LocalTime startTime = LocalTime.parse(showTime);
                    LocalTime endTime = startTime.plusMinutes(movie.getDuration());
                    ShowTime interval = new ShowTime(startTime, endTime);
                    List<Schedule> schedule = scheduleDAO.listScheduleByMovieDate(theatreId, movieId, showDate);
                    errorMessage = Validation.checkScheduleConflict(schedule, interval);
                }

                // Check for space conflict
                if (errorMessage.isEmpty()) {
                    LocalTime startTime = LocalTime.parse(showTime);
                    LocalTime endTime = startTime.plusMinutes(movie.getDuration());
                    ShowTime interval = new ShowTime(startTime, endTime);
                    List<Schedule> schedule = scheduleDAO.listScheduleByRoomDate(theatreId, roomNumber, showDate);
                    errorMessage = Validation.checkScheduleConflict(schedule, interval);
                }

                if (errorMessage.isEmpty()) {
                    errorMessage = scheduleDAO.addSchedule(theatreId, roomNumber, movieId, showDate, showTime);
                }
                if (!errorMessage.isEmpty()) {
                    session.setAttribute("errorMessage", errorMessage);
                    session.setAttribute(SHOW_DATE, showDate);
                    session.setAttribute(START_TIME, showTime);
                    session.setAttribute(ROOM_NUMBER, roomNumber);
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
