package com.epam.edp.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.Map;

@RestController
public class S3Controller {

    private static final String BUCKET = "cmtr-ooyx672z";
    private static final String KEY = "data.txt";

    private final S3Client s3Client;

    public S3Controller() {
        this.s3Client = S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.EU_CENTRAL_1)
                .build();
    }

    @GetMapping("/")
    public Map<String, String> getContent() {
        ResponseBytes<GetObjectResponse> object = s3Client.getObjectAsBytes(request ->
                request
                        .bucket(BUCKET)
                        .key(KEY)
        );

        String content = object.asUtf8String();

        return Map.of("content", content);
    }
}
