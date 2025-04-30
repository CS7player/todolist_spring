package com.p001.todolist.model;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String username;
 private String password;

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

 public String getUsername() {
  return username;
 }

 public void setUsername(String username) {
  this.username = username;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public Long getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(Long createdAt) {
  this.createdAt = createdAt;
 }
}
