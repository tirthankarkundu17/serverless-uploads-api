package in.bitmaskers.service;

import in.bitmaskers.entity.AppResponse;

import java.util.Map;

public interface UploadService {
    AppResponse processCreatePresignedURL(Map<String, Object> input);
}
