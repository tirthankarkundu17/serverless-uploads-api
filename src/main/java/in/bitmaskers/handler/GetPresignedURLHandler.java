package in.bitmaskers.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import in.bitmaskers.entity.ApiGatewayResponse;
import in.bitmaskers.entity.AppResponse;
import in.bitmaskers.service.UploadService;
import in.bitmaskers.service.UploadServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class GetPresignedURLHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        LOG.info("Received request");
        UploadService uploadService = new UploadServiceImpl();
        AppResponse response = uploadService.processCreatePresignedURL(input);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return ApiGatewayResponse.builder()
                .setStatusCode(response.getStatusCode())
                .setRawBody(response.getResponseBody())
                .setHeaders(headers)
                .build();
    }
}
