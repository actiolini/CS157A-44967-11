package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import moviebuddy.util.DBConnection;
import moviebuddy.model.Receipt;
import moviebuddy.model.Movie;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReceiptDAO {
    public Receipt getReceiptInfo(int scheduleID, String seatNumber, int theatreId) throws Exception {
        String QUERY_TICKET = "";
        Connection conn = DBConnection.connect();
        PreparedStatement getReceipt = conn.prepareStatement(QUERY_TICKET);
        ResultSet res = getReceipt.executeQuery();
        Receipt receipt = null;
        while (res.next()) {
            receipt = new Receipt();
            

        }
        getReceipt.close();
        conn.close();
        return receipt;
    }

    public List<Receipt> listReceipts() throws Exception {
        String QUERY_RECEIPT_INFO = "SELECT movie_id, title, release_date, duration, poster, trailer, description FROM movie ORDER BY release_date DESC;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryReceiptsInfo = conn.prepareStatement(QUERY_RECEIPT_INFO);
        ResultSet res = queryReceiptsInfo.executeQuery();
        List<Receipt> recipts = new ArrayList<>();
        while (res.next()) {
            Receipt recipt = new Receipt();
            

            recipts.add(recipt);
        }
        queryReceiptsInfo.close();
        conn.close();
        return recipts;
    }

    public String addReceipt(String scheduleID, String accountID, String quantity) throws Exception{
    // accountID  theatreName movieName showTime  showDate quantity totalPrice ticketPrice
        String INSERT_RECEIPT_INFO = "INSERT INTO `receipt`(account_id, theatre_name, movie_title, show_time, show_date, quantity, total_price) VALUES(?,?,?,?,?,?,?)"; 
        String QUERY_MOVIE_INFO = "SELECT t.theatre_name, m.title, ms.show_date, ms.show_time, tp.price" +
        "FROM movie_schedule ms" +
        "JOIN theatre t ON ms.theatre_id=t.theatre_id" +
        "JOIN movie m ON ms.movie_id=m.movie_id" +
        "JOIN ticket_price tp ON t.theatre_id = tp.theatre_id AND ms.show_time = tp.start_time" +
        "WHERE schedule_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryMovieInfo = conn.prepareStatement(QUERY_MOVIE_INFO);
        queryMovieInfo.setString(1, scheduleID);
        ResultSet res = queryMovieInfo.executeQuery();
        
        String theatreName = res.getString("theatre_name");
        String movieName = res.getString("title");
        String showTime = res.getString("show_time");
        String showDate = res.getString("show_date");
        Double totalPrice = res.getDouble("price") * Integer.parseInt(quantity);

        queryMovieInfo.close();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertReceiptInfo = conn.prepareStatement(INSERT_RECEIPT_INFO);
            insertReceiptInfo.setString(1, accountID);
            insertReceiptInfo.setString(2, theatreName);
            insertReceiptInfo.setString(3, movieName);
            insertReceiptInfo.setString(4, showTime);
            insertReceiptInfo.setString(5, showDate);
            insertReceiptInfo.setString(6, quantity);
            insertReceiptInfo.setString(7, String.valueOf(totalPrice));
            insertReceiptInfo.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to upload movie info";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
}
}
