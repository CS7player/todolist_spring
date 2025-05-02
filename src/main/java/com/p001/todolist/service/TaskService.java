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

 // Adding new Task!!!
 public int insertTask(Long userId, String taskDescription) {
  String sql = "INSERT INTO task (user_id, task,status, created_at) VALUES (?, ?,0, ?)";
  long createdAt = Instant.now().getEpochSecond();
  return jdbcTemplate.update(sql, userId, taskDescription, createdAt);
 }

 // Getting the Record based on the User_id!!!
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

 // change the status which selected and not selected mapped with the user_id!!!
 public int updateStatus(List<Integer> taskIds, Long userId) {
  if (userId == null) {
   return 0;
  }
  int updatedRows = 0;
  if (taskIds != null && !taskIds.isEmpty()) {
   String placeholders = String.join(",", taskIds.stream().map(id -> "?").toList());
   String sqlSet1 = "UPDATE task SET status = 1 WHERE user_id = ? AND id IN (" + placeholders + ")";
   Object[] params1 = new Object[taskIds.size() + 1];
   params1[0] = userId;
   for (int i = 0; i < taskIds.size(); i++) {
    params1[i + 1] = taskIds.get(i);
   }
   updatedRows += jdbcTemplate.update(sqlSet1, params1);
   String sqlSet0 = "UPDATE task SET status = 0 WHERE user_id = ? AND id NOT IN (" + placeholders + ")";
   Object[] params2 = new Object[taskIds.size() + 1];
   params2[0] = userId;
   for (int i = 0; i < taskIds.size(); i++) {
    params2[i + 1] = taskIds.get(i);
   }
   updatedRows += jdbcTemplate.update(sqlSet0, params2);
  } else {
   String sqlSet0 = "UPDATE task SET status = 0 WHERE user_id = ?";
   updatedRows = jdbcTemplate.update(sqlSet0, userId);
  }
  return updatedRows;
 }

}
