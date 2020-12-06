package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import moviebuddy.model.Movie;
import moviebuddy.util.DBConnection;

public class MovieDAO {
    public String uploadMovieInfo(String title, String releaseDate, String duration, String trailer, InputStream poster,
            long posterSize, String description) throws Exception {
        String INSERT_MOVIE_INFO = "INSERT INTO movie (title, release_date, duration, trailer, description) VALUES (?,?,?,?,?)";
        String QUERY_MOVIE_ID = "SELECT LAST_INSERT_ID() as id";
        String UPDATE_MOVIE_POSTER = "UPDATE movie SET poster=? WHERE movie_id = LAST_INSERT_ID();";
        int posterId = -1;
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement insertMovieInfo = conn.prepareStatement(INSERT_MOVIE_INFO);
            insertMovieInfo.setString(1, title);
            insertMovieInfo.setDate(2, Date.valueOf(releaseDate));
            insertMovieInfo.setInt(3, Integer.parseInt(duration));
            insertMovieInfo.setString(4, trailer);
            insertMovieInfo.setString(5, description);
            insertMovieInfo.executeUpdate();
            PreparedStatement getMovieId = conn.prepareStatement(QUERY_MOVIE_ID);
            ResultSet res = getMovieId.executeQuery();
            while (res.next()) {
                posterId = res.getInt("id");
            }
            if (posterId < 0) {
                return "Fail to get movie id";
            }
            String posterURL = BuddyBucket.uploadPoster(posterId, poster, posterSize);
            if (posterURL.isEmpty()) {
                return "Fail to upload poster";
            }
            PreparedStatement updateMoviePoster = conn.prepareStatement(UPDATE_MOVIE_POSTER);
            updateMoviePoster.setString(1, posterURL);
            updateMoviePoster.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return "Fail to upload movie info";
        } finally {
            conn.setAutoCommit(true);
        }
        return "";
    }

    public List<Movie> getMoviesInfo() throws Exception {
        String QUERY_MOVIES_INFO = "SELECT movie_id, title, release_date, duration, poster, trailer, description FROM movie ORDER BY release_date DESC;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryMoviesInfo = conn.prepareStatement(QUERY_MOVIES_INFO);
        ResultSet res = queryMoviesInfo.executeQuery();
        List<Movie> movies = new ArrayList<>();
        while (res.next()) {
            Movie movie = new Movie(res.getInt("movie_id"));
            movie.setTitle(res.getString("title"));
            movie.setReleaseDate(LocalDate.parse(res.getString("release_date")));
            movie.setDuration(res.getInt("duration"));
            movie.setPoster(res.getString("poster"));
            movie.setTrailer(res.getString("trailer"));
            movie.setDescription(res.getString("description"));
            movies.add(movie);
        }
        queryMoviesInfo.close();
        conn.close();
        return movies;
    }

    public Movie getMovieInfo(int movieId) throws Exception {
        String QUERY_MOVIE_INFO = "SELECT title, release_date, duration, trailer, description FROM movie WHERE movie_id=?;";
        Connection conn = DBConnection.connect();
        PreparedStatement queryMovieInfo = conn.prepareStatement(QUERY_MOVIE_INFO);
        queryMovieInfo.setInt(1, movieId);
        ResultSet res = queryMovieInfo.executeQuery();
        Movie movie = new Movie(movieId);
        while (res.next()) {
            movie.setTitle(res.getString("title"));
            movie.setReleaseDate(LocalDate.parse(res.getString("release_date")));
            movie.setDuration(res.getInt("duration"));
            movie.setTrailer(res.getString("trailer"));
            movie.setDescription(res.getString("description"));
        }
        queryMovieInfo.close();
        conn.close();
        return movie;
    }

    public void updateMovieInfo(String movieId, String title, String releaseDate, String duration, String trailer,
            InputStream poster, long posterSize, String description) throws Exception {
        String UPDATE_MOVIE_INFO = "UPDATE movie SET title=?, release_date=?, duration=?, trailer=?,poster=?, description=? WHERE movie_id = ?;";
        int id = Integer.parseInt(movieId);
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            String posterURL = BuddyBucket.uploadPoster(id, poster, posterSize);
            PreparedStatement updateMovieInfo = conn.prepareStatement(UPDATE_MOVIE_INFO);
            updateMovieInfo.setString(1, title);
            updateMovieInfo.setDate(2, Date.valueOf(releaseDate));
            updateMovieInfo.setInt(3, Integer.parseInt(duration));
            updateMovieInfo.setString(4, trailer);
            updateMovieInfo.setString(5, posterURL);
            updateMovieInfo.setString(6, description);
            updateMovieInfo.setInt(7, id);
            updateMovieInfo.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public void deleteMovieInfo(String movieId) throws Exception {
        String DELETE_MOVIE_INFO = "DELETE FROM movie WHERE movie_id=?";
        int id = Integer.parseInt(movieId);
        Connection conn = DBConnection.connect();
        conn.setAutoCommit(false);
        try {
            PreparedStatement updateMovieInfo = conn.prepareStatement(DELETE_MOVIE_INFO);
            updateMovieInfo.setInt(1, id);
            updateMovieInfo.executeUpdate();
            conn.commit();
            BuddyBucket.deletePoster(id);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                System.out.println("Transaction is being rolled back");
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            conn.setAutoCommit(true);
        }
    }
}