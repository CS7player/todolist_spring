package com.p001.todolist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.p001.todolist.model.Profile;
import com.p001.todolist.repository.ProfileRepository;
import com.p001.todolist.service.S3Service;

@RestController
@RequestMapping("/profile")
public class ProfileController {

 private final S3Service s3Service;
 private final ProfileRepository profileRepository;

 public ProfileController(S3Service s3Service, ProfileRepository profileRepository) {
  this.s3Service = s3Service;
  this.profileRepository = profileRepository;
 }

 @GetMapping("/upload-url")
 public ResponseEntity<Map<String, String>> getUploadUrl(@RequestParam String filename) {
  String url = s3Service.generateUploadUrl(filename);
  return ResponseEntity.ok(Map.of("url", url));
 }

 @GetMapping("/download-url")
 public ResponseEntity<Map<String, String>> getDownloadUrl(@RequestParam String key) {
  String url = s3Service.generateDownloadUrl(key);
  return ResponseEntity.ok(Map.of("url", url));
 }

 @PostMapping("/add")
 public ResponseEntity<?> addFile(@RequestBody Map<String, Object> body) {
  String filename = (String) body.get("filename");
  Object userIdObj = body.get("user_id");

  // Validate that filename and user_id are present
  if (filename == null || userIdObj == null) {
   return ResponseEntity.badRequest().body(Map.of("error", "filename and user_id are required"));
  }

  Long userId;
  try {
   // Safely convert user_id to Long
   userId = Long.parseLong(userIdObj.toString());
  } catch (NumberFormatException e) {
   return ResponseEntity.badRequest().body(Map.of("error", "Invalid user_id"));
  }

  // Create and save the Profile
  Profile image = new Profile();
  image.setFileName(filename);
  image.setUserId(userId);
  profileRepository.save(image);

  return ResponseEntity.ok(Map.of("status", "success"));
 }

 @GetMapping("/get")
 public ResponseEntity<List<Profile>> getAllImages() {
  List<Profile> profiles = (List<Profile>) profileRepository.findAll();
  return ResponseEntity.ok(profiles);
 }
}
