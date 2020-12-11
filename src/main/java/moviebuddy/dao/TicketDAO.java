package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import moviebuddy.util.DBConnection;
import moviebuddy.model.Ticket;

public class TicketDAO {
    public Ticket getTicketInfo(int scheduleID, String seatNumber, boolean occupid) throws Exception{
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
}
