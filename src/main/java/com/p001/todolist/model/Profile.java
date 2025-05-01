package com.p001.todolist.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile")
public class Profile {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(name = "user_id") // Ensure it matches the database column name
 private Long userId;

 @Column(name = "filename")
 private String filename;

 @Column(name = "created_at")
 private Long createdAt;

 @PrePersist
 protected void onCreate() {
  this.createdAt = Instant.now().getEpochSecond(); // Unix timestamp
 }

 // Getters and setters
 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getUserId() {
  return userId;
 }

 public void setUserId(Long userId) {
  this.userId = userId;
 }

 public String getFileName() {
  return filename;
 }

 public void setFileName(String filename) {
  this.filename = filename;
 }

 public Long getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(Long createdAt) {
  this.createdAt = createdAt;
 }
}
