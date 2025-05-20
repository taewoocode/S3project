package com.example.s3test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3AsyncClient s3Client;
    private static final long PART_SIZE = 5 * 1024 * 1024; // 5MB

    public void uploadFile(String key, File file) throws Exception {
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();

        String uploadId = s3Client.createMultipartUpload(createRequest).get().uploadId();

        List<CompletableFuture<CompletedPart>> futures = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            long fileSize = file.length();
            int partNumber = 1;
            byte[] buffer = new byte[(int) PART_SIZE];
            int bytesRead;
            long offset = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                final int currentPartNumber = partNumber++;
                final byte[] partData = Arrays.copyOf(buffer, bytesRead);

                UploadPartRequest uploadRequest = getUploadPartRequest(key, uploadId,
                    currentPartNumber, partData);

                CompletableFuture<CompletedPart> future = getFuture(uploadRequest, partData, currentPartNumber);
                futures.add(future);
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
            .parts(futures.stream()
                .map(CompletableFuture::join)
                .sorted(Comparator.comparingInt(CompletedPart::partNumber))
                .collect(Collectors.toList()))
            .build();

        s3Client.completeMultipartUpload(CompleteMultipartUploadRequest.builder()
            .bucket(bucket)
            .key(key)
            .uploadId(uploadId)
            .multipartUpload(completedMultipartUpload)
            .build()).get();
    }

    private UploadPartRequest getUploadPartRequest(String key, String uploadId, int currentPartNumber,
        byte[] partData) {
        UploadPartRequest uploadRequest = UploadPartRequest.builder()
            .bucket(bucket)
            .key(key)
            .uploadId(uploadId)
            .partNumber(currentPartNumber)
            .contentLength((long) partData.length)
            .build();
        return uploadRequest;
    }

    private CompletableFuture<CompletedPart> getFuture(UploadPartRequest uploadRequest, byte[] partData,
        int currentPartNumber) {
        return s3Client.uploadPart(uploadRequest, AsyncRequestBody.fromBytes(partData))
            .thenApply(response -> CompletedPart.builder()
                .partNumber(currentPartNumber)
                .eTag(response.eTag())
                .build());
    }
}
