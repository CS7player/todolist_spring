package com.p001.todolist.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
public class S3Service {

 @Value("${aws.bucketName}")
 private String bucketName;

 private final S3Client s3Client;
 private final S3Presigner s3Presigner;

 public S3Service(S3Client s3Client, S3Presigner s3Presigner) {
  this.s3Client = s3Client;
  this.s3Presigner = s3Presigner;
 }

 public String generateUploadUrl(String filename) {
  PutObjectRequest request = PutObjectRequest.builder()
    .bucket(bucketName)
    .key("uploads/" + filename)
    .contentType("image/jpeg")
    .build();

  PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
    .signatureDuration(Duration.ofMinutes(5))
    .putObjectRequest(request)
    .build();

  return s3Presigner.presignPutObject(presignRequest).url().toString();
 }

 public String generateDownloadUrl(String key) {
  GetObjectRequest request = GetObjectRequest.builder()
    .bucket(bucketName)
    .key(key)
    .build();

  GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
    .signatureDuration(Duration.ofHours(1))
    .getObjectRequest(request)
    .build();

  return s3Presigner.presignGetObject(presignRequest).url().toString();
 }
}
