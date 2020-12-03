package moviebuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import java.io.InputStream;

import moviebuddy.util.DBConnection;

public class MovieDAO {
    public String upload(String title, String releaseDate, String duration, String trailer, InputStream poster,
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
}