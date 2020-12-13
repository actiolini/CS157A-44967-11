package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import moviebuddy.util.DBConnection;
import moviebuddy.model.Receipt;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReceiptDAO {
    // for guest user, not completed
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

    // for registered user, show all history orders
    public List<Receipt> listReceipts(String accountID) throws Exception {
        String QUERY_RECEIPT_INFO = "SELECT r.receipt_id, r.movie_title, r.show_date, r.show_time, r.quantity, total_price, seat_number" +
        "FROM receipt r JOIN booked b ON r.receipt_id = b.recipt_id" + 
        "WHERE r.account_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryReceiptsInfo = conn.prepareStatement(QUERY_RECEIPT_INFO);
        queryReceiptsInfo.setString(1, accountID);
        ResultSet res = queryReceiptsInfo.executeQuery();
        List<Receipt> receipts = new ArrayList<>();
        Set<Integer> receiptIDs = new HashSet<Integer>();
        while (res.next()) {
            int receiptID = res.getInt("receipt_id");
            if (!receiptIDs.contains(receiptID)) {
                receiptIDs.add(receiptID);
                Receipt receipt = new Receipt();
                receipt.setReceiptID(receiptID);
                receipt.setMovieName(res.getString("movie_title"));
                receipt.setShowDate(LocalDate.parse(res.getString("show_date")));
                receipt.setShowTime(LocalTime.parse(res.getString("show_time")));
                receipt.setQuantity(res.getInt("quantity"));
                receipt.settotalPrice(res.getDouble("total_price"));
                receipt.setSeatNumbers();
                receipt.getSeatNumbers().add(res.getString("seat_number"));
                receipts.add(receipt);
            }else{
                receipts.get(receipts.size()-1).getSeatNumbers().add(res.getString("seat_number"));
            }
        }
        queryReceiptsInfo.close();
        conn.close();
        return receipts;
    }

    //add receipt/order into DB, for checkout
    public String addReceipt(String scheduleID, String accountID, String quantity) throws Exception {
        String INSERT_RECEIPT_INFO = "INSERT INTO `receipt`(account_id, theatre_name, movie_title, show_time, show_date, quantity, total_price) VALUES(?,?,?,?,?,?,?)";
        String QUERY_MOVIE_INFO = "SELECT t.theatre_name, m.title, ms.show_date, ms.show_time"
                + "FROM movie_schedule ms" + "JOIN theatre t ON ms.theatre_id=t.theatre_id"
                + "JOIN movie m ON ms.movie_id=m.movie_id" + "WHERE schedule_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryMovieInfo = conn.prepareStatement(QUERY_MOVIE_INFO);
        queryMovieInfo.setString(1, scheduleID);
        ResultSet res = queryMovieInfo.executeQuery();

        String theatreName = res.getString("theatre_name");
        String movieName = res.getString("title");
        String showTime = res.getString("show_time");
        String showDate = res.getString("show_date");
        Double totalPrice = res.getDouble("price") * Integer.parseInt(quantity);// need to be changed

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
