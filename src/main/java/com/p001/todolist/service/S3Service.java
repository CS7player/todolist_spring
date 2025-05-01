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

 @Value("${aws.accessKey}")
 private String accessKey;

 @Value("${aws.secretKey}")
 private String secretKey;

 private final S3Client s3Client;

 public S3Service(S3Client s3Client) {
  this.s3Client = s3Client;
 }

 public String generateUploadUrl(String filename) {
  PutObjectRequest request = PutObjectRequest.builder()
    .bucket(bucketName)
    .key("uploads/" + filename)
    .contentType("image/jpeg")
    .build();

  return getPresignedUrl(request);
 }

 public String generateDownloadUrl(String key) {
  GetObjectRequest request = GetObjectRequest.builder()
    .bucket(bucketName)
    .key(key)
    .build();

  return getPresignedUrl(request);
 }

 private String getPresignedUrl(Object request) {
  // Create the S3Presigner with credentials
  S3Presigner presigner = S3Presigner.builder()
    .region(Region.of("us-east-1")) // Adjust the region as necessary
    .credentialsProvider(StaticCredentialsProvider.create(
      AwsBasicCredentials.create(accessKey, secretKey))) // Using Spring @Value
    .build();

  if (request instanceof PutObjectRequest) {
   PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
     .signatureDuration(Duration.ofMinutes(5))
     .putObjectRequest((PutObjectRequest) request)
     .build();
   return presigner.presignPutObject(presignRequest).url().toString();
  } else if (request instanceof GetObjectRequest) {
   GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
     .signatureDuration(Duration.ofHours(1))
     .getObjectRequest((GetObjectRequest) request)
     .build();
   return presigner.presignGetObject(presignRequest).url().toString();
  }

  return null; // You can return an error or throw an exception here for invalid request type
 }
}
