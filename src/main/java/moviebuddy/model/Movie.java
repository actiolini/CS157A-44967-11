package moviebuddy.model;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie {
    private int id;
    private String title;
    private int duration;
    private LocalDate releaseDate;
    private String trailer;
    private String poster;
    private String description;
    private List<Schedule> schedule;

    public Movie(int id) {
        this.id = id;
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

    public String displayReleaseDate() {
        return DateTimeFormatter.ofPattern("MMM dd yyy").format(releaseDate);
    }

    public String getTrailer() {
        return trailer;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }
}
