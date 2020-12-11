package moviebuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Receipt {
    
    private int receiptID; // order id
    private int accountID;
    private String theatreName;
    private String movieName;
    private LocalTime showTime;
    private LocalDate showDate;
    private int quantity;
    private double totalPrice;
    private double ticketPrice;
    private Ticket[] tickets;

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

    public Ticket[] getTickets() {
        return tickets;
    }

    public double getTicketPrice() {
        return ticketPrice;
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

    public void setTickets(Ticket[] tickects) {
        this.tickets = tickects;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
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
