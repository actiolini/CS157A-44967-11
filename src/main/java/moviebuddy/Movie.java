package moviebuddy;

import java.util.List;
import java.time.LocalDate;

public class Movie {
    private int id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String description;
    private List<Schedule> schedule;

    public Movie(int id, String title, int duration, LocalDate releaseDate, String description,
            List<Schedule> schedule) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.description = description;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }
}
