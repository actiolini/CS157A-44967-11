package moviebuddy.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private int scheduleId;
    private int theatreId;
    private int movieId;
    private LocalDate showDate;
    private List<ShowTime> showTimes;

    public Schedule(int scheduleId) {
        this.scheduleId = scheduleId;
        this.showTimes = new ArrayList<>();
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

    public LocalDate getShowDate() {
        return showDate;
    }

    public String displayShowDate() {
        return DateTimeFormatter.ofPattern("MM/dd").format(showDate);
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setShowDate(LocalDate showDate) {
        this.showDate = showDate;
    }

    public void addShowTime(LocalTime startTime, LocalTime endTime, int roomNumber) {
        showTimes.add(new ShowTime(startTime, endTime, roomNumber));
    }

}