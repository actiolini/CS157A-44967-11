package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.time.LocalTime;

import moviebuddy.util.DBConnection;
import moviebuddy.db.RoomDB;
import moviebuddy.db.MovieDB;
import moviebuddy.db.ScheduleDB;
import moviebuddy.db.TicketDB;
import moviebuddy.model.Schedule;
import moviebuddy.model.TicketPrice;
import moviebuddy.model.ScheduledDate;
import moviebuddy.model.Ticket;

public class ScheduleDAO {

    public List<Schedule> listScheduleByMovie(String theatreId, String movieId) throws Exception {
        String QUERY_SCHEDULES = String.format(
            "SELECT sc.%s, sc.%s, sc.%s, sc.%s, sc.%s, sc.%s, m.%s FROM %s sc JOIN %s m ON m.%s=sc.%s WHERE sc.%s=? AND sc.%s=? ORDER BY sc.%s, sc.%s;",
            ScheduleDB.SCHEDULE_ID, ScheduleDB.THEATRE_ID, ScheduleDB.ROOM_NUMBER,
            ScheduleDB.MOVIE_ID, ScheduleDB.SHOW_DATE, ScheduleDB.SHOW_TIME,
            MovieDB.DURATION, ScheduleDB.TABLE, MovieDB.TABLE,
            MovieDB.MOVIE_ID, ScheduleDB.MOVIE_ID, ScheduleDB.THEATRE_ID,
            ScheduleDB.MOVIE_ID, ScheduleDB.SHOW_DATE, ScheduleDB.SHOW_TIME
        );

        List<Schedule> schedules = new LinkedList<>();
        Connection conn = null;
        PreparedStatement querySchedules = null;
        try {
            conn = DBConnection.connect();
            querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
            querySchedules.setString(1, theatreId);
            querySchedules.setString(2, movieId);
            ResultSet res = querySchedules.executeQuery();
            while (res.next()) {
                Schedule schedule = new Schedule(res.getInt(ScheduleDB.SCHEDULE_ID));
                schedule.setTheatreId(res.getInt(ScheduleDB.THEATRE_ID));
                schedule.setRoomNumber(res.getInt(ScheduleDB.ROOM_NUMBER));
                schedule.setMovieId(res.getInt(ScheduleDB.MOVIE_ID));
                schedule.setShowDate(LocalDate.parse(res.getString(ScheduleDB.SHOW_DATE)));
                LocalTime startTime = LocalTime.parse(res.getString(ScheduleDB.SHOW_TIME));
                LocalTime endTime = startTime.plusMinutes(res.getInt(MovieDB.DURATION));
                schedule.setShowTime(startTime, endTime);
                schedules.add(schedule);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            DBConnection.close(querySchedules);
            DBConnection.close(conn);
        }
        return schedules;
    }

    public List<Schedule> listScheduleByMovieDate(String theatreId, String movieId, String showDate) throws Exception {
        String QUERY_SCHEDULES = "SELECT schedule_id, show_time, room_number FROM schedule WHERE theatre_id=? AND movie_id=? AND show_date=? ORDER BY show_time;";

        List<Schedule> schedules = new LinkedList<>();
        Connection conn = null;
        PreparedStatement querySchedules = null;
        try {
            conn = DBConnection.connect();
            querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
            querySchedules.setString(1, theatreId);
            querySchedules.setString(2, movieId);
            querySchedules.setString(3, showDate);
            ResultSet res2 = querySchedules.executeQuery();
            while (res2.next()) {
                Schedule schedule = new Schedule(res2.getInt("schedule_id"));
                schedule.setTheatreId(Integer.parseInt(theatreId));
                schedule.setMovieId(Integer.parseInt(movieId));
                schedule.setRoomNumber(res2.getInt("room_number"));
                schedule.setShowDate(LocalDate.parse(showDate));
                LocalTime startTime = LocalTime.parse(res2.getString("show_time"));
                LocalTime endTime = startTime.plusMinutes(0);
                schedule.setShowTime(startTime, endTime);
                schedules.add(schedule);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            DBConnection.close(querySchedules);
            DBConnection.close(conn);
        }
        return schedules;
    }

    public List<Schedule> listScheduleByRoomDate(String theatreId, String roomNumber, String showDate)
            throws Exception {
        String QUERY_SCHEDULES = "SELECT ms.schedule_id, ms.movie_id, ms.show_time, ms.room_number, m.duration FROM schedule ms JOIN movie m ON ms.movie_id=m.movie_id WHERE theatre_id=? AND room_number=? AND show_date=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement querySchedules = conn.prepareStatement(QUERY_SCHEDULES);
        querySchedules.setString(1, theatreId);
        querySchedules.setString(2, roomNumber);
        querySchedules.setString(3, showDate);
        ResultSet res = querySchedules.executeQuery();
        List<Schedule> schedules = new LinkedList<>();
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

    public String addSchedule(String theatreId, String roomNumber, String movieId, String showDate, String startTime)
            throws Exception {
        String INSERT_SCHEDULE = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?);",
            ScheduleDB.TABLE, ScheduleDB.THEATRE_ID, ScheduleDB.ROOM_NUMBER,
            ScheduleDB.MOVIE_ID, ScheduleDB.SHOW_DATE, ScheduleDB.SHOW_TIME
        );
        String QUERY_SCHEDULE_ID = String.format(
            "SELECT LAST_INSERT_ID() as %s;",
            ScheduleDB.SCHEDULE_ID
        );
        String QUERY_ROOM_CAPACITY = String.format(
            "SELECT %s, %s FROM %s WHERE %s=? AND %s=?;",
            RoomDB.SECTIONS, RoomDB.SEATS, RoomDB.TABLE,
            RoomDB.THEATRE_ID, RoomDB.ROOM_NUMBER
        );
        String INSERT_TICKET = String.format(
            "INSERT INTO %s (%s, %s) VALUES (?, ?);",
            TicketDB.TABLE, TicketDB.SCHEDULE_ID, TicketDB.SEAT_NUMBER
        );

        Connection conn = null;
        PreparedStatement insertSchedule = null;
        PreparedStatement queryScheduleId = null;
        PreparedStatement queryRoomCapacity = null;
        PreparedStatement insertTicket = null;
        try {
            conn = DBConnection.connect();
            conn.setAutoCommit(false);

            insertSchedule = conn.prepareStatement(INSERT_SCHEDULE);
            insertSchedule.setString(1, theatreId);
            insertSchedule.setString(2, roomNumber);
            insertSchedule.setString(3, movieId);
            insertSchedule.setString(4, showDate);
            insertSchedule.setString(5, startTime);
            insertSchedule.executeUpdate();

            String scheduleId = "";
            queryScheduleId = conn.prepareStatement(QUERY_SCHEDULE_ID);
            ResultSet resScheduleId = queryScheduleId.executeQuery();
            while (resScheduleId.next()) {
                scheduleId = resScheduleId.getString(ScheduleDB.SCHEDULE_ID);
            }

            int sections = 0, seats = 0;
            queryRoomCapacity = conn.prepareStatement(QUERY_ROOM_CAPACITY);
            queryRoomCapacity.setString(1, theatreId);
            queryRoomCapacity.setString(2, roomNumber);
            ResultSet resRoomCap = queryRoomCapacity.executeQuery();
            while (resRoomCap.next()) {
                sections = resRoomCap.getInt(RoomDB.SECTIONS);
                seats = resRoomCap.getInt(RoomDB.SEATS);
            }

            insertTicket = conn.prepareStatement(INSERT_TICKET);
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
            DBConnection.close(insertSchedule);
            DBConnection.close(queryScheduleId);
            DBConnection.close(queryRoomCapacity);
            DBConnection.close(insertTicket);
            DBConnection.close(conn);
        }
        return "";
    }

    public String deleteSchedule(String scheduleId) throws Exception {
        String DELETE_TICKET = String.format(
            "DELETE FROM %s WHERE %s=?;",
            TicketDB.TABLE, TicketDB.SCHEDULE_ID
        );
        String DELETE_SCHEDULE = String.format(
            "DELETE FROM %s WHERE %s=?;",
            ScheduleDB.TABLE, ScheduleDB.SCHEDULE_ID
        );
        Connection conn = null;
        PreparedStatement deleteTicket = null;
        PreparedStatement deleteSchedule = null;
        try {
            conn = DBConnection.connect();
            conn.setAutoCommit(false);

            deleteTicket = conn.prepareStatement(DELETE_TICKET);
            deleteTicket.setString(1, scheduleId);
            deleteTicket.executeUpdate();

            deleteSchedule = conn.prepareStatement(DELETE_SCHEDULE);
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
            DBConnection.close(deleteTicket);
            DBConnection.close(deleteSchedule);
            DBConnection.close(conn);
        }
        return "";
    }

    public Ticket getTicketInfo(int scheduleID, String seatNumber, boolean occupid) throws Exception {
        String QUERY_TICKET = "";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicket = conn.prepareStatement(QUERY_TICKET);
        ResultSet res = getTicket.executeQuery();
        Ticket ticket = null;
        while (res.next()) {
            ticket = new Ticket();
            ticket.setScheduleId(res.getInt("schedule_id"));
            ticket.setSeatNumber(res.getString("seat_number"));
            ticket.setOccupied(res.getBoolean("occupid"));
        }
        getTicket.close();
        conn.close();
        return ticket;
    }

    public double getTicketPrice(String showTime, String ScheduleID) throws Exception{
        String QUERY_TICKET_PRICE = "SELECT DISTINCT tp.start_time, tp.price" +
        "FROM ticket_price tp" +  
        "JOIN schedule ms ON tp.theatre_id=ms.theatre_id" +
        "JOIN ticket t ON ms.schedule_id=t.schedule_id" + 
        "WHERE t.schedule_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicketPrice = conn.prepareStatement(QUERY_TICKET_PRICE);
        getTicketPrice.setString(1, ScheduleID);
        ResultSet res = getTicketPrice.executeQuery();
        List<TicketPrice> ticketPrices = new LinkedList<>();
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