package moviebuddy.model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private int scheduleId;
    private int theatreId;
    private String theatreName;
    private String seatNumber;
    private double price;
    private LocalTime showTime;
    private LocalDate showDate;

    public int getScheduleId() {
        return scheduleId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public double getPrice() {
        return price;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public LocalTime getShowTime() {
        return showTime;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public void setShowTime() {
        this.showTime=showTime;
    }

    public void setShowDate() {
        this.showDate=showDate;
    }

    public String getFormattedDate() {
        return this.showDate.format(DateTimeFormatter.ofPattern("MM/dd"));
    }
}
