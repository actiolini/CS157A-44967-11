package moviebuddy.model;

import java.io.StringBufferInputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Receipt {
    
    private int receiptID; // order id
    private int accountID;
    private String theatreName;
    private String movieName;
    private LocalTime showTime;
    private LocalDate showDate;
    private int quantity;
    private double totalPrice;
    private ArrayList<String> seatNumbers;

    public int getQuantity() {
        return quantity;
    }

    public int getReceiptID() {
        return receiptID;
    }

    public int getaccountID() {
        return accountID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<String> getSeatNumbers() {
        return seatNumbers;
    }


    public String getTheatreName() {
        return theatreName;
    }

    public String getMovieName() {
        return movieName;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public void setReceiptID(int receiptID) {
        this.receiptID = receiptID;
    }

    public void settotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSeatNumbers() {
        //initialization
        this.seatNumbers = new ArrayList<>();
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setShowTime(LocalTime showTime) {
        this.showTime = showTime;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;

    }
}
