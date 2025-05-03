package com.p001.todolist.config;

import io.github.cdimascio.dotenv.Dotenv;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

 @Bean
 public DataSource dataSource() {
  Dotenv dotenv = Dotenv.load();

  HikariDataSource ds = new HikariDataSource();
  ds.setJdbcUrl(dotenv.get("DB_URL"));
  ds.setUsername(dotenv.get("DB_USERNAME"));
  ds.setPassword(dotenv.get("DB_PASSWORD"));
  return ds;
 }

 @Bean
 public AwsS3Config awsS3Config() {
  Dotenv dotenv = Dotenv.load();
  System.setProperty("ACCESS_KEY_ID", dotenv.get("ACCESS_KEY_ID"));
  System.setProperty("SECRET_ACCESS_KEY", dotenv.get("SECRET_ACCESS_KEY"));
  System.setProperty("BUCKET_NAME", dotenv.get("BUCKET_NAME"));
  System.setProperty("REGION", dotenv.get("REGION"));


  // String accessKeyId = dotenv.get("ACCESS_KEY_ID");
  // String secretAccessKey = dotenv.get("SECRET_ACCESS_KEY");
  // Assuming you have a class like AwsS3Config to store the credentials
  AwsS3Config awsS3Config = new AwsS3Config();
  // awsS3Config.setAccessKeyId(accessKeyId);
  // awsS3Config.setSecretAccessKey(secretAccessKey);
  return awsS3Config;
 }
}
