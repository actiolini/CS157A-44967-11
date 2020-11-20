package moviebuddy.dao;

import java.io.InputStream;

public class UploadMovieDAO {
    public void upload(InputStream poster) {
        int posterId = 1;
        BuddyBucket.uploadPoster(posterId, poster);
    }
}
