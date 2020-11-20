package moviebuddy.dao;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.InputStream;

public class BuddyBucket {
    private static final String BUCKET = "moviebuddy-157-001-011";
    private static final String POSTER = "posters/";

    public static void uploadPoster(int posterId, InputStream posterContent) {
        try {
            AWSCredentials credentials = new ProfileCredentialsProvider("default").getCredentials();
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.US_WEST_1)
                    .build();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            PutObjectRequest request = new PutObjectRequest(BUCKET, POSTER + posterId, posterContent, metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException ase) {
            serverExceptionMessage(ase);
        } catch (AmazonClientException ace) {
            clientExceptionMessage(ace);
        }
    }

    private static void serverExceptionMessage(AmazonServiceException ase) {
        System.out.println("Request made it to S3, but was rejected with an error response.");
        System.out.println("Error Message:    " + ase.getMessage());
        System.out.println("HTTP Status Code: " + ase.getStatusCode());
        System.out.println("AWS Error Code:   " + ase.getErrorCode());
        System.out.println("Error Type:       " + ase.getErrorType());
        System.out.println("Request ID:       " + ase.getRequestId());
    }

    private static void clientExceptionMessage(AmazonClientException ace) {
        System.out.println(
                "Client encountered a serious internal problem while trying to communicate with S3, such as not being able to access the network.");
        System.out.println("Error Message: " + ace.getMessage());
    }
}
