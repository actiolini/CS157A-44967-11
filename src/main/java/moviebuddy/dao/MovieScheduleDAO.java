package moviebuddy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.LocalTime;

import moviebuddy.model.*;

public class MovieScheduleDAO {
    public List<Movie> getMovieSchedule() throws ClassNotFoundException {
        String QUERY_MOVIE_SCHEDULE = "SELECT schedule_id, show_date, show_time, movie_id FROM movie_schedule WHERE theatre_id = ? ;";

        String QUERY_MOVIE_INFO = "SELECT title, duration, release_date, description FROM movie WHERE movie_id = ?";

        List<Movie> movies = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            Connection con = DriverManager.getConnection(MyDB.driver, MyDB.user, MyDB.pass);

            Map<Integer, Map<LocalDate, Schedule>> schedules = new HashMap<>();
            PreparedStatement preparedStatement = con.prepareStatement(QUERY_MOVIE_SCHEDULE);
            preparedStatement.setInt(1, 1);// theatre_id = 1
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                int movieId = res.getInt("movie_id");
                LocalDate showDate = LocalDate.parse(res.getString("show_date"));
                if (!schedules.containsKey(movieId)) {
                    schedules.put(movieId, new HashMap<>());
                }
                Map<LocalDate, Schedule> scheduleByDate = schedules.get(movieId);
                if (!scheduleByDate.containsKey(showDate)) {
                    scheduleByDate.put(showDate, new Schedule(res.getInt("schedule_id"), movieId, showDate));
                }
                scheduleByDate.get(showDate).getShowTimes().add(LocalTime.parse(res.getString("show_time")));
            }

            preparedStatement = con.prepareStatement(QUERY_MOVIE_INFO);
            Iterator<Integer> iter = schedules.keySet().iterator();
            while (iter.hasNext()) {
                int movieId = iter.next();
                List<Schedule> scheduleList = new ArrayList<>(schedules.get(movieId).values());
                for (Schedule schedule : scheduleList) {
                    schedule.getShowTimes().sort((t1, t2) -> {
                        return t1.compareTo(t2);
                    });
                }
                scheduleList.sort((s1, s2) -> {
                    return s1.getShowDate().compareTo(s2.getShowDate());
                });
                preparedStatement.setInt(1, movieId);
                res = preparedStatement.executeQuery();
                while (res.next()) {
                    movies.add(new Movie(movieId, res.getString("title"), res.getInt("duration"),
                            LocalDate.parse(res.getString("release_date")), res.getString("description"),
                            scheduleList));
                }
            }
            con.close();
        } catch (SQLException e) {
            printSQLException(e);
        }
        movies.sort((m1, m2) -> {
            return m1.getReleaseDate().compareTo(m2.getReleaseDate());
        });
        return movies;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}