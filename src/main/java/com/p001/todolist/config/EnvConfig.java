package com.p001.todolist.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class EnvConfig {

 @Bean
 public DataSource dataSource() {
  // Get environment variables directly
  String dbUrl = System.getenv("DB_URL");
  String dbUsername = System.getenv("DB_USERNAME");
  String dbPassword = System.getenv("DB_PASSWORD");

  // Create the HikariDataSource and configure it
  HikariDataSource ds = new HikariDataSource();
  ds.setJdbcUrl(dbUrl);
  ds.setUsername(dbUsername);
  ds.setPassword(dbPassword);
  return ds;
 }

 @Bean
 public AwsS3Config awsS3Config() {
  // Get AWS credentials from environment variables
  String accessKeyId = System.getenv("ACCESS_KEY_ID");
  String secretAccessKey = System.getenv("SECRET_ACCESS_KEY");
  String bucketName = System.getenv("BUCKET_NAME");
  String region = System.getenv("REGION");

  // Set AWS properties as system properties (if required by the AWS SDK)
  System.setProperty("ACCESS_KEY_ID", accessKeyId);
  System.setProperty("SECRET_ACCESS_KEY", secretAccessKey);
  System.setProperty("BUCKET_NAME", bucketName);
  System.setProperty("REGION", region);

  // Create and return the AWS S3 configuration
  AwsS3Config awsS3Config = new AwsS3Config();
  return awsS3Config;
 }
}
