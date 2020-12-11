package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import moviebuddy.util.DBConnection;
import moviebuddy.model.Ticket;

public class TicketDAO {
    public Ticket getTicketInfo(int scheduleID, String seatNumber, int theatreId) throws Exception{
        String QUERY_TICKET = "SELECT schedule_id, seat_number, price FROM ticket, ticket_price, schedule, movie, theatre, room, WHERE schedule_id;";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicket = conn.prepareStatement(QUERY_TICKET);
        ResultSet res = getTicket.executeQuery();
        Ticket ticket = null;
        while (res.next()) {
            ticket = new Ticket();
            ticket.setScheduleId(res.getInt("schedule_id"));
            ticket.setTheatreId(res.getInt("theatre_id"));
            ticket.setPrice(res.getDouble("price"));
            ticket.setSeatNumber(res.getString("seat_number"));
            ticket.setTheatreName(res.getString("theatre_name"));
            ticket.setShowTime(res.getString("start_time"));
            ticket.setShowDate(res.getString("date"));
            ticket.setMovieName(res.getString("movie_name"));
        }
        getTicket.close();
        conn.close();
        return ticket;
    }
}
