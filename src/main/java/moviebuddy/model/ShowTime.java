package moviebuddy.model;

import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;

public class ShowTime implements Comparable<ShowTime> {
    private LocalTime startTime;
    private LocalTime endTime;

    public ShowTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public boolean isConflict(ShowTime timeInterval) {
        if (this.startTime.isAfter(timeInterval.endTime) || this.endTime.isBefore(timeInterval.startTime)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ShowTime timeInterval) {
        return this.startTime.compareTo(timeInterval.getStartTime());
    }

    @Override
    public String toString() {
        return String.format("%s-%s", startTime, endTime);
    }
}