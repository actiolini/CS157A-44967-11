package moviebuddy.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private int scheduleId;
    private int theatreId;
    private int roomNumber;
    private int movieId;
    private LocalDate showDate;
    private ShowTime showTime;

    public Schedule(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public String displayShowDate() {
        return DateTimeFormatter.ofPattern("MM/dd/yyyy").format(showDate);
    }

    public ShowTime getShowTime() {
        return showTime;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public void setShowTime(LocalTime startTime, LocalTime endTime) {
        this.showTime = new ShowTime(startTime, endTime);
    }

}