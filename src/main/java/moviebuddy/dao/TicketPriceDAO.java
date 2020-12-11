package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import moviebuddy.util.DBConnection;
import moviebuddy.model.TicketPrice;
import java.time.LocalTime;

public class TicketPriceDAO {
    public TicketPrice getTicketPrice(String theatreID, String startTime ) throws Exception{
        String QUERY_TICKET_PRICE = "SELECT price FROM ticket_price WHERE theatre_id =? AND start_time=?; ";
        Connection conn = DBConnection.connect();
        PreparedStatement getTicketPrice = conn.prepareStatement(QUERY_TICKET_PRICE);
        getTicketPrice.setString(1, theatreID);
        getTicketPrice.setString(2, startTime);
        ResultSet res = getTicketPrice.executeQuery();
        TicketPrice ticketPrice = new TicketPrice(res.getInt("theatre_id"),LocalTime.parse(res.getString("start_time")));
        ticketPrice.setPrice(res.getDouble("price"));
        getTicketPrice.close();
        conn.close();
        return ticketPrice;
    }
}
