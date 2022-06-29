package in.bitmaskers.utility;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class S3Util {
    private static S3Util s3Util = null;
    private final AmazonS3 s3Client;

    public static S3Util getInstance() {
        if (s3Util == null)
            s3Util = new S3Util();
        return s3Util;
    }

    private S3Util() {
        String region = System.getenv().getOrDefault("REGION", "us-east-2");
        s3Client = AmazonS3ClientBuilder.standard().withRegion(region).build();
    }

    public String createPresignedURL(String bucketName, String key, long ttlInSeconds) {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += TimeUnit.SECONDS.toMillis(ttlInSeconds);
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration);
        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}
