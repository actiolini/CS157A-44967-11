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
import moviebuddy.model.ScheduledDate;
import moviebuddy.util.DBConnection;

public class ScheduleDAO {
    public String addSchedule(String theatreId, String roomNumber, String movieId, String showDate, String startTime)
            throws Exception {
        String INSERT_SCHEDULE = "INSERT INTO movie_schedule (theatre_id, room_number, movie_id, show_date, show_time) VALUES (?,?,?,?,?);";
        String QUERY_SCHEDULE_ID = "SELECT LAST_INSERT_ID() as id;";
        String QUERY_ROOM_CAPACITY = "SELECT sections, seats FROM room WHERE theatre_id=? AND room_number=?;";
        String INSERT_TICKET = "INSERT INTO ticket (schedule_id, seat_number) VALUES (?, ?);";
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
            String scheduleId = "";
            PreparedStatement queryScheduleId = conn.prepareStatement(QUERY_SCHEDULE_ID);
            ResultSet res1 = queryScheduleId.executeQuery();
            while (res1.next()) {
                scheduleId = res1.getString("id");
            }
            int sections = 0;
            int seats = 0;
            PreparedStatement queryRoomCapacity = conn.prepareStatement(QUERY_ROOM_CAPACITY);
            queryRoomCapacity.setString(1, theatreId);
            queryRoomCapacity.setString(2, roomNumber);
            ResultSet res2 = queryRoomCapacity.executeQuery();
            while (res2.next()) {
                sections = res2.getInt("sections");
                seats = res2.getInt("seats");
            }
            PreparedStatement insertTicket = conn.prepareStatement(INSERT_TICKET);
            for (int i = 0; i < sections; i++) {
                char sectionLetter = (char) ('A' + i);
                for (int j = 1; j <= seats; j++) {
                    String seatNumber = sectionLetter + ((j < 10) ? "0" : "") + j;
                    insertTicket.setString(1, scheduleId);
                    insertTicket.setString(2, seatNumber);
                    insertTicket.executeUpdate();
                }
            }
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
            conn.close();
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
        conn.close();
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
        conn.close();
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
        conn.close();
        return schedules;
    }

    public List<ScheduledDate> listScheduledDates() throws Exception{
        return null;
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
            conn.close();
        }
        return "";
    }
}