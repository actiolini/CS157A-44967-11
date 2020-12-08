package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalTime;

import moviebuddy.model.Theatre;
import moviebuddy.model.Room;
import moviebuddy.model.TicketPrice;
import moviebuddy.util.DBConnection;

public class TheatreDAO {
    public String createTheatre(String theatreName, String address, String city, String state, String country,
            String zip) throws Exception {
        String INSERT_THEATRE = "INSERT INTO theatre (theatre_name, address, city, state, country, zip_code) VALUES(?,?,?,?,?,?);";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertTheatre = conn.prepareStatement(INSERT_THEATRE);
            insertTheatre.setString(1, theatreName);
            insertTheatre.setString(2, address);
            insertTheatre.setString(3, city);
            insertTheatre.setString(4, state);
            insertTheatre.setString(5, country);
            insertTheatre.setString(6, zip);
            insertTheatre.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to create theatre";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public List<Theatre> listTheatres() throws Exception {
        String QUERY_THEATRES = "SELECT theatre_id, theatre_name, address, city, state, country, zip_code FROM theatre ORDER BY theatre_name;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryTheatres = conn.prepareStatement(QUERY_THEATRES);
        ResultSet res = queryTheatres.executeQuery();
        List<Theatre> theatres = new ArrayList<>();
        while (res.next()) {
            Theatre theatre = new Theatre(res.getInt("theatre_id"));
            theatre.setTheatreName(res.getString("theatre_name"));
            theatre.setAddress(res.getString("address"));
            theatre.setCity(res.getString("city"));
            theatre.setState(res.getString("state"));
            theatre.setCountry(res.getString("country"));
            theatre.setZip(res.getString("zip_code"));
            theatres.add(theatre);
        }
        queryTheatres.close();
        conn.close();
        return theatres;
    }

    public Theatre getTheatreById(int theatreId) throws Exception {
        String QUERY_THEATRE = "SELECT theatre_name, address, city, state, country, zip_code FROM theatre WHERE theatre_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryTheatre = conn.prepareStatement(QUERY_THEATRE);
        queryTheatre.setInt(1, theatreId);
        ResultSet res = queryTheatre.executeQuery();
        Theatre theatre = null;
        while (res.next()) {
            theatre = new Theatre(theatreId);
            theatre.setTheatreName(res.getString("theatre_name"));
            theatre.setAddress(res.getString("address"));
            theatre.setCity(res.getString("city"));
            theatre.setState(res.getString("state"));
            theatre.setCountry(res.getString("country"));
            theatre.setZip(res.getString("zip_code"));
        }
        queryTheatre.close();
        conn.close();
        return theatre;
    }

    public Theatre getTheatreByName(String theatreName) throws Exception {
        String QUERY_THEATRE = "SELECT theatre_id, address, city, state, country, zip_code FROM theatre WHERE theatre_name=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryTheatre = conn.prepareStatement(QUERY_THEATRE);
        queryTheatre.setString(1, theatreName);
        ResultSet res = queryTheatre.executeQuery();
        Theatre theatre = null;
        while (res.next()) {
            theatre = new Theatre(res.getInt("theatre_id"));
            theatre.setTheatreName(theatreName);
            theatre.setAddress(res.getString("address"));
            theatre.setCity(res.getString("city"));
            theatre.setState(res.getString("state"));
            theatre.setCountry(res.getString("country"));
            theatre.setZip(res.getString("zip_code"));
        }
        queryTheatre.close();
        conn.close();
        return theatre;
    }

    public String updateTheatre(int theatreId, String theatreName, String address, String city, String state,
            String country, String zip) throws Exception {
        String UPDATE_THEATRE = "UPDATE theatre SET theatre_name=?, address=?, city=?, state=?, country=?, zip_code=? WHERE theatre_id=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement updateTheatre = conn.prepareStatement(UPDATE_THEATRE);
            updateTheatre.setString(1, theatreName);
            updateTheatre.setString(2, address);
            updateTheatre.setString(3, city);
            updateTheatre.setString(4, state);
            updateTheatre.setString(5, country);
            updateTheatre.setString(6, zip);
            updateTheatre.setInt(7, theatreId);
            updateTheatre.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to update theatre";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public String deleteTheatre(int theatreId) throws Exception {
        String DELETE_MANAGE = "DELETE FROM manage WHERE theatre_id=?;";
        String DELETE_TICKET_PRICE = "DELETE FROM ticket_price WHERE theatre_id=?;";
        String DELETE_SCHEDULE = "DELETE FROM movie_schedule WHERE theatre_id=?;";
        String DELETE_ROOM = "DELETE FROM room WHERE theatre_id=?;";
        String DELETE_THEATRE = "DELETE FROM theatre WHERE theatre_id=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement deleteManage = conn.prepareStatement(DELETE_MANAGE);
            deleteManage.setInt(1, theatreId);
            deleteManage.executeUpdate();
            PreparedStatement deleteTicketPrice = conn.prepareStatement(DELETE_TICKET_PRICE);
            deleteTicketPrice.setInt(1, theatreId);
            deleteTicketPrice.executeUpdate();
            PreparedStatement deleteSchedule = conn.prepareStatement(DELETE_SCHEDULE);
            deleteSchedule.setInt(1, theatreId);
            deleteSchedule.executeUpdate();
            PreparedStatement deleteRoom = conn.prepareStatement(DELETE_ROOM);
            deleteRoom.setInt(1, theatreId);
            deleteRoom.executeUpdate();
            PreparedStatement deleteTheatre = conn.prepareStatement(DELETE_THEATRE);
            deleteTheatre.setInt(1, theatreId);
            deleteTheatre.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to delete theatre";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public String createRoom(int theatreId, int roomNumber, int sections, int seats) throws Exception {
        String INSERT_ROOM = "INSERT INTO room (theatre_id, room_number, sections, seats) VALUES (?,?,?,?);";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertRoom = conn.prepareStatement(INSERT_ROOM);
            insertRoom.setInt(1, theatreId);
            insertRoom.setInt(2, roomNumber);
            insertRoom.setInt(3, sections);
            insertRoom.setInt(4, seats);
            insertRoom.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to create room";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public List<Room> listRooms(int theatreId) throws Exception {
        String QUERY_ROOMS = "SELECT room_number, sections, seats FROM room WHERE theatre_id=? ORDER BY room_number;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryRooms = conn.prepareStatement(QUERY_ROOMS);
        queryRooms.setInt(1, theatreId);
        ResultSet res = queryRooms.executeQuery();
        List<Room> rooms = new ArrayList<>();
        while (res.next()) {
            Room room = new Room(theatreId, res.getInt("room_number"));
            room.setNumberOfRows(res.getInt("sections"));
            room.setSeatsPerRow(res.getInt("seats"));
            rooms.add(room);
        }
        queryRooms.close();
        conn.close();
        return rooms;
    }

    public Room getRoomById(int theatreId, int roomNumber) throws Exception {
        String QUERY_ROOM = "SELECT sections, seats FROM room WHERE theatre_id=? AND room_number=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryRoom = conn.prepareStatement(QUERY_ROOM);
        queryRoom.setInt(1, theatreId);
        queryRoom.setInt(2, roomNumber);
        ResultSet res = queryRoom.executeQuery();
        Room room = null;
        while (res.next()) {
            room = new Room(theatreId, roomNumber);
            room.setNumberOfRows(res.getInt("sections"));
            room.setSeatsPerRow(res.getInt("seats"));
        }
        queryRoom.close();
        conn.close();
        return room;
    }

    public String updateRoom(int theatreId, int roomId, int roomNumber, int sections, int seats) throws Exception {
        String UPDATE_ROOM = "UPDATE room SET room_number=?, sections=?, seats=? WHERE theatre_id=? AND room_number=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement updateRoom = conn.prepareStatement(UPDATE_ROOM);
            updateRoom.setInt(1, roomNumber);
            updateRoom.setInt(2, sections);
            updateRoom.setInt(3, seats);
            updateRoom.setInt(4, theatreId);
            updateRoom.setInt(5, roomId);
            updateRoom.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to update room";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public String deleteRoom(int theatreId, int roomNumber) throws Exception {
        String DELETE_SCHEDULE = "DELETE FROM movie_schedule WHERE theatre_id=? AND room_number=?;";
        String DELETE_ROOM = "DELETE FROM room WHERE theatre_id=? AND room_number=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement deleteSchedule = conn.prepareStatement(DELETE_SCHEDULE);
            deleteSchedule.setInt(1, theatreId);
            deleteSchedule.setInt(2, roomNumber);
            deleteSchedule.executeUpdate();
            PreparedStatement deleteRoom = conn.prepareStatement(DELETE_ROOM);
            deleteRoom.setInt(1, theatreId);
            deleteRoom.setInt(2, roomNumber);
            deleteRoom.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to delete room";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public String addTicketPrice(int theatreId, String startTime, double price) throws Exception {
        String INSERT_TICKET_PRICE = "INSERT INTO ticket_price (theatre_id, start_time, price) VALUES (?,?,?);";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertTicketPrice = conn.prepareStatement(INSERT_TICKET_PRICE);
            insertTicketPrice.setInt(1, theatreId);
            insertTicketPrice.setString(2, startTime);
            insertTicketPrice.setDouble(3, price);
            insertTicketPrice.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to add ticket price";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public List<TicketPrice> listTicketPrices(int theatreId) throws Exception {
        String QUERY_TICKET_PRICES = "SELECT start_time, price FROM ticket_price WHERE theatre_id=? ORDER BY start_time;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryTicketPrices = conn.prepareStatement(QUERY_TICKET_PRICES);
        queryTicketPrices.setInt(1, theatreId);
        ResultSet res = queryTicketPrices.executeQuery();
        List<TicketPrice> ticketPrices = new ArrayList<>();
        while (res.next()) {
            TicketPrice ticketPrice = new TicketPrice(theatreId, LocalTime.parse(res.getString("start_time")));
            ticketPrice.setPrice(res.getDouble("price"));
            ticketPrices.add(ticketPrice);
        }
        queryTicketPrices.close();
        conn.close();
        return ticketPrices;
    }

    public TicketPrice getTicketPrice(int theatreId, String startTime) throws Exception {
        String QUERY_TICKET_PRICE = "SELECT start_time, price FROM ticket_price WHERE theatre_id=? AND start_time=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryTicketPrice = conn.prepareStatement(QUERY_TICKET_PRICE);
        queryTicketPrice.setInt(1, theatreId);
        queryTicketPrice.setString(2, startTime);
        ResultSet res = queryTicketPrice.executeQuery();
        TicketPrice ticketPrice = null;
        while (res.next()) {
            ticketPrice = new TicketPrice(theatreId, LocalTime.parse(res.getString("start_time")));
            ticketPrice.setPrice(res.getDouble("price"));
        }
        return ticketPrice;
    }

    public String deleteTicketPrice(int theatreId, String startTime) throws Exception {
        String DELETE_TICKET_PRICE = "DELETE FROM ticket_price WHERE theatre_id=? AND start_time=?;";
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement deleteTicketPrice = conn.prepareStatement(DELETE_TICKET_PRICE);
            deleteTicketPrice.setInt(1, theatreId);
            deleteTicketPrice.setString(2, startTime);
            deleteTicketPrice.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to delete ticket price";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }
}
