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
@Table(name = "task")
public class Task {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private Long user_id;
 private String task;
 private int status;
 @Column(name = "created_at")
 private Long createdAt;

 @PrePersist
 protected void onCreate() {
  this.createdAt = Instant.now().getEpochSecond(); // Unix timestamp
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Long getUserId() {
  return user_id;
 }

 public void setUserId(Long user_id) {
  this.user_id = user_id;
 }

 public String getTask() {
  return task;
 }

 public void setTask(String task) {
  this.task = task;
 }

 public int getStatus() {
  return status;
 }

 public void setStatus(int status) {
  this.status = status;
 }

 public Long getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(Long createdAt) {
  this.createdAt = createdAt;
 }
}
