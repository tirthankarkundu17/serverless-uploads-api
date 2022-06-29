package in.bitmaskers.service;

import com.google.gson.Gson;
import in.bitmaskers.dto.CreatePresignedURLDto;
import in.bitmaskers.entity.AppResponse;
import in.bitmaskers.utility.S3Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UploadServiceImpl implements UploadService {
    private final Logger LOG = LogManager.getLogger(this.getClass());
    private final Gson gson = new Gson();
    private final S3Util s3Util = S3Util.getInstance();

    @Override
    public AppResponse processCreatePresignedURL(Map<String, Object> input) {
        AppResponse response = new AppResponse();
        int statusCode = HttpsURLConnection.HTTP_OK;
        String data = "{}";
        String requestId = UUID.randomUUID().toString();
        Map<String, String> resp = new HashMap<>();
        resp.put("requestId", requestId);
        try {
            String body = (String) input.get("body");
            CreatePresignedURLDto presignedURLDto = gson.fromJson(body, CreatePresignedURLDto.class);
            String bucketName = System.getenv("BUCKET_NAME");
            LOG.info(requestId + "|Creating Presigned URL");
            String presignedURL = s3Util.createPresignedURL(bucketName, presignedURLDto.getFileName(), presignedURLDto.getTtlInSeconds());
            LOG.info(requestId + "|Created Presigned URL");
            resp.put("status", "Success");
            resp.put("presignedURL", presignedURL);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpsURLConnection.HTTP_INTERNAL_ERROR);
            response.setResponseBody("Error occurred at our end.");
        }
        data = gson.toJson(resp);
        response.setStatusCode(statusCode);
        response.setResponseBody(data);

        return response;
    }
}
