package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import moviebuddy.util.DBConnection;
import moviebuddy.model.Ticket;
import moviebuddy.model.Schedule;
import moviebuddy.model.Movie;

public class TicketDAO {

    public Ticket getTicketInfo(int ScheduleId, String seatNumber) throws Exception {
        String QUERY_TICKET = " ";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicket = conn.prepareStatement(QUERY_TICKET);
        ResultSet res = getTicket.executeQuery();
        Ticket ticket = null;
        while (res.next()) {
            ticket = new Ticket();
            ticket.setScheduleId(res.getInt("schedule_id"));
            ticket.setSeatNumber(res.getString("seat_number"));
        }
        getTicket.close();
        conn.close();
        return ticket;
    }
    
}
