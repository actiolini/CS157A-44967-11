package moviebuddy.model;

import java.time.LocalTime;

public class TicketPrice {
    private int theatreId;
    private LocalTime startTime;
    private double price;

    public TicketPrice(int theatreId, LocalTime startTime) {
        this.theatreId = theatreId;
        this.startTime = startTime;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public double getPrice() {
        return price;
    }

    public String displayPrice() {
        return String.format("%.2f", price);
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
