package moviebuddy.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;



public class ScheduledDate {
    private LocalDate showDate;
    private List<Movie> movies;

    public ScheduledDate(LocalDate showDate){
        this.showDate = showDate;
        movies = new ArrayList<>();
    }

    public LocalDate getShowDate() {
        return showDate;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie){
        movies.add(movie);
    }
}
