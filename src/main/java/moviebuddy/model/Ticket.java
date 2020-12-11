package moviebuddy.model;
public class Ticket {
    private int scheduleId;
    private int theatreId;
    private String theatreName;
    private String seatNumber;
    private double price;
    private String showTime;
    private String showDate;
    private String movieName;

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

    public String getShowTime() {
        return showTime;
    }

    public String getShowDate() {
        return showDate;
    }

    public String getMovieName(){
        return movieName;
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

    public void setShowTime(String showTime) {
        this.showTime=showTime;
    }

    public void setShowDate(String showDate) {
        this.showDate=showDate;
    }
    
    public void setMovieName(String movieName) {
        this.movieName=movieName;
    }
}
