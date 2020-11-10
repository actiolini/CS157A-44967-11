package moviebuddy;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Schedule {
    private int scheduleId;
    private int movieId;
    private LocalDate showDate;
    private List<LocalTime> showTimes;

    public Schedule(int scheduleId, int movieId, LocalDate showDate) {
        this.scheduleId = scheduleId;
        this.movieId = movieId;
        this.showDate = showDate;
        this.showTimes = new ArrayList<>();
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getMovieId() {
        return movieId;
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public String getFormattedDate() {
        return this.showDate.format(DateTimeFormatter.ofPattern("MM/dd"));
    }

    public List<LocalTime> getShowTimes() {
        return showTimes;
    }
}