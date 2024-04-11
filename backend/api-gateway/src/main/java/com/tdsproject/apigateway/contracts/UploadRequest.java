package com.tdsproject.apigateway.contracts;

import org.springframework.web.multipart.MultipartFile;

public record UploadRequest(
        MultipartFile file
) {
}
