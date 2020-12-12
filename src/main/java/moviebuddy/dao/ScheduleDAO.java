package moviebuddy.dao;

import java.sql.Connection;
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

import moviebuddy.model.Movie;
import moviebuddy.model.Schedule;
import moviebuddy.model.TicketPrice;
import moviebuddy.util.DBConnection;

public class ScheduleDAO {
    // public List<Movie> getMovieSchedule() throws Exception {
    // String QUERY_MOVIE_SCHEDULE = "SELECT schedule_id, show_date, show_time,
    // movie_id FROM movie_schedule WHERE theatre_id = ? ;";

    // String QUERY_MOVIE_INFO = "SELECT title, duration, release_date, description
    // FROM movie WHERE movie_id = ?";

    // List<Movie> movies = new ArrayList<>();
    // Map<Integer, Map<LocalDate, Schedule>> schedules = new HashMap<>();
    // // Initiate connection
    // Connection conn = DBConnection.connect();

    // // Query for all schedules
    // PreparedStatement preparedStatement =
    // conn.prepareStatement(QUERY_MOVIE_SCHEDULE);
    // preparedStatement.setInt(1, 1);// theatre_id = 1
    // ResultSet res = preparedStatement.executeQuery();
    // while (res.next()) {
    // int movieId = res.getInt("movie_id");
    // LocalDate showDate = LocalDate.parse(res.getString("show_date"));
    // if (!schedules.containsKey(movieId)) {
    // schedules.put(movieId, new HashMap<>());
    // }
    // Map<LocalDate, Schedule> scheduleByDate = schedules.get(movieId);
    // if (!scheduleByDate.containsKey(showDate)) {
    // scheduleByDate.put(showDate, new Schedule(res.getInt("schedule_id"), movieId,
    // showDate));
    // }
    // LocalTime showTime = LocalTime.parse(res.getString("show_time"));
    // scheduleByDate.get(showDate).getShowTimes().add(showTime);
    // }

    // // Query for movie information that are scheduled
    // preparedStatement = conn.prepareStatement(QUERY_MOVIE_INFO);
    // Iterator<Integer> iter = schedules.keySet().iterator();
    // while (iter.hasNext()) {
    // int movieId = iter.next();
    // List<Schedule> scheduleList = new
    // ArrayList<>(schedules.get(movieId).values());
    // for (Schedule schedule : scheduleList) {
    // schedule.getShowTimes().sort((t1, t2) -> {
    // return t1.compareTo(t2);
    // });
    // }
    // scheduleList.sort((s1, s2) -> {
    // return s1.getShowDate().compareTo(s2.getShowDate());
    // });
    // preparedStatement.setInt(1, movieId);
    // res = preparedStatement.executeQuery();
    // while (res.next()) {
    // Movie movie = new Movie(movieId);
    // movie.setTitle(res.getString("title"));
    // movie.setDuration(res.getInt("duration"));
    // movie.setReleaseDate(LocalDate.parse(res.getString("release_date")));
    // movie.setDescription(res.getString("description"));
    // movie.setSchedule(scheduleList);
    // movies.add(movie);
    // }
    // }
    // conn.close();
    // movies.sort((m1, m2) -> {
    // return m1.getReleaseDate().compareTo(m2.getReleaseDate());
    // });
    // return movies;
    // }

    public String addSchedule(String theatreId, String roomNumber, String movieId, String showDate, String startTime)
            throws Exception {
        String INSERT_SCHEDULE = "INSERT INTO movie_schedule (theatre_id, room_number, movie_id, show_date, show_time) VALUES (?,?,?,?,?)";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertSchedule = conn.prepareStatement(INSERT_SCHEDULE);
            insertSchedule.setString(1, theatreId);
            insertSchedule.setString(2, roomNumber);
            insertSchedule.setString(3, movieId);
            insertSchedule.setString(4, showDate);
            insertSchedule.setString(5, startTime);
            insertSchedule.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to add schedule";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public List<Schedule> listScheduleByMovie(String theatreId, String movieId) throws Exception {
        String QUERY_MOVIE_DURATION = "SELECT duration FROM movie WHERE movie_id=?;";
        String QUERY_SCHEDULES = "SELECT schedule_id, show_date, show_time, room_number FROM movie_schedule WHERE theatre_id=? AND movie_id=? ORDER BY show_date, show_time;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryDuration = conn.prepareStatement(QUERY_MOVIE_DURATION);
        queryDuration.setString(1, movieId);
        ResultSet res1 = queryDuration.executeQuery();
        int duration = 0;
        while (res1.next()) {
            duration = res1.getInt("duration");
        }
        PreparedStatement querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
        querySchedules.setString(1, theatreId);
        querySchedules.setString(2, movieId);
        ResultSet res2 = querySchedules.executeQuery();
        List<Schedule> schedules = new ArrayList<>();
        while (res2.next()) {
            Schedule schedule = new Schedule(res2.getInt("schedule_id"));
            schedule.setTheatreId(Integer.parseInt(theatreId));
            schedule.setMovieId(Integer.parseInt(movieId));
            schedule.setRoomNumber(res2.getInt("room_number"));
            schedule.setShowDate(LocalDate.parse(res2.getString("show_date")));
            LocalTime startTime = LocalTime.parse(res2.getString("show_time"));
            LocalTime endTime = startTime.plusMinutes(duration);
            schedule.setShowTime(startTime, endTime);
            schedules.add(schedule);
        }
        return schedules;
    }

    public List<Schedule> listScheduleByMovieDate(String theatreId, String movieId, String showDate) throws Exception {
        String QUERY_MOVIE_DURATION = "SELECT duration FROM movie WHERE movie_id=?;";
        String QUERY_SCHEDULES = "SELECT schedule_id, show_time, room_number FROM movie_schedule WHERE theatre_id=? AND movie_id=? AND show_date=? ORDER BY show_time;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryDuration = conn.prepareStatement(QUERY_MOVIE_DURATION);
        queryDuration.setString(1, movieId);
        ResultSet res1 = queryDuration.executeQuery();
        int duration = 0;
        while (res1.next()) {
            duration = res1.getInt("duration");
        }
        PreparedStatement querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
        querySchedules.setString(1, theatreId);
        querySchedules.setString(2, movieId);
        querySchedules.setString(3, showDate);
        ResultSet res2 = querySchedules.executeQuery();
        List<Schedule> schedules = new ArrayList<>();
        while (res2.next()) {
            Schedule schedule = new Schedule(res2.getInt("schedule_id"));
            schedule.setTheatreId(Integer.parseInt(theatreId));
            schedule.setMovieId(Integer.parseInt(movieId));
            schedule.setRoomNumber(res2.getInt("room_number"));
            schedule.setShowDate(LocalDate.parse(showDate));
            LocalTime startTime = LocalTime.parse(res2.getString("show_time"));
            LocalTime endTime = startTime.plusMinutes(duration);
            schedule.setShowTime(startTime, endTime);
            schedules.add(schedule);
        }
        return schedules;
    }

    public List<Schedule> listScheduleByRoomDate(String theatreId, String roomNumber, String showDate)
            throws Exception {
        String QUERY_SCHEDULES = "SELECT ms.schedule_id, ms.movie_id, ms.show_time, ms.room_number, m.duration FROM movie_schedule ms JOIN movie m ON ms.movie_id=m.movie_id WHERE theatre_id=? AND room_number=? AND show_date=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
        querySchedules.setString(1, theatreId);
        querySchedules.setString(2, roomNumber);
        querySchedules.setString(3, showDate);
        ResultSet res = querySchedules.executeQuery();
        List<Schedule> schedules = new ArrayList<>();
        while (res.next()) {
            Schedule schedule = new Schedule(res.getInt("schedule_id"));
            schedule.setTheatreId(Integer.parseInt(theatreId));
            schedule.setMovieId(res.getInt("movie_id"));
            schedule.setRoomNumber(res.getInt("room_number"));
            schedule.setShowDate(LocalDate.parse(showDate));
            LocalTime startTime = LocalTime.parse(res.getString("show_time"));
            LocalTime endTime = startTime.plusMinutes(res.getInt("duration"));
            schedule.setShowTime(startTime, endTime);
            schedules.add(schedule);
        }
        return schedules;
    }

    public String deleteSchedule(String scheduleId) throws Exception {
        String DELETE_SCHEDULE = "DELETE FROM movie_schedule WHERE schedule_id=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement deleteSchedule = conn.prepareStatement(DELETE_SCHEDULE);
            deleteSchedule.setString(1, scheduleId);
            deleteSchedule.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to delete schedule";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public double getTicketPrice(String showTime, String ScheduleID) throws Exception{
        String QUERY_TICKET_PRICE = "SELECT DISTINCT tp.start_time, tp.price" +
        "FROM ticket_price tp" +  
        "JOIN movie_schedule ms ON tp.theatre_id=ms.theatre_id" +
        "JOIN ticket t ON ms.schedule_id=t.schedule_id" + 
        "WHERE t.schedule_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicketPrice = conn.prepareStatement(QUERY_TICKET_PRICE);
        getTicketPrice.setString(1, ScheduleID);
        ResultSet res = getTicketPrice.executeQuery();
        List<TicketPrice> ticketPrices = new ArrayList<>();
        while (res.next()) {
            TicketPrice ticketPrice = new TicketPrice(res.getInt("theatre_id"),LocalTime.parse(res.getString("start_time")));
            ticketPrice.setPrice(res.getDouble("price"));
            ticketPrices.add(ticketPrice);
        }
        getTicketPrice.close();
        conn.close();
        for(int i =0;i<ticketPrices.size();i++){
            if(LocalTime.parse(showTime).isAfter(ticketPrices.get(i).getStartTime()) || i==ticketPrices.size()-1)
            return ticketPrices.get(i).getPrice();
        }
        return 0;   
    }
}