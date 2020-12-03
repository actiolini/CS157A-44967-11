package moviebuddy.dao;

import java.io.InputStream;

public class MovieDAO {
    public String upload(String title, String releaseDate, String duration, String trailer, InputStream poster,
            String description) {

        int posterId = 1;
        return BuddyBucket.uploadPoster(posterId, poster);
    }
}
