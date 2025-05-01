package com.p001.todolist.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.p001.todolist.model.Task;

@Service
public class TaskService {

 @Autowired
 private JdbcTemplate jdbcTemplate;

 public int insertTask(Long userId, String taskDescription) {
  String sql = "INSERT INTO task (user_id, task,status, created_at) VALUES (?, ?,0, ?)";
  long createdAt = Instant.now().getEpochSecond();

  return jdbcTemplate.update(sql, userId, taskDescription, createdAt);
 }

 public List<Task> getTasksByUserId(Long userId) {
  String sql = "SELECT id, user_id, task,status, created_at FROM task WHERE user_id = ?";

  RowMapper<Task> rowMapper = (rs, rowNum) -> {
   Task task = new Task();
   task.setId(rs.getLong("id"));
   task.setUserId(rs.getLong("user_id"));
   task.setTask(rs.getString("task"));
   task.setStatus(rs.getInt("status"));
   task.setCreatedAt(rs.getLong("created_at"));
   return task;
  };

  return jdbcTemplate.query(sql, rowMapper, userId);
 }

}
