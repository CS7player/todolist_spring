package com.p001.todolist.controller;

import com.p001.todolist.model.Task;
import com.p001.todolist.service.TaskService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

 private final TaskService taskService;

 public TaskController(TaskService taskService) {
  this.taskService = taskService;
 }

 @PostMapping("/add")
 public ResponseEntity<Map<String, Object>> addTask(@RequestBody Map<String, Object> request) {
  Map<String, Object> response = new HashMap<>();
  try {
   Long userId = Long.valueOf(request.get("user_id").toString());
   String taskText = request.get("task").toString();
   int rowsAffected = taskService.insertTask(userId, taskText);
   if (rowsAffected > 0) {
    response.put("status", true);
    response.put("message", "Task added successfully");
   } else {
    response.put("status", false);
    response.put("message", "No task was added");
   }
   return ResponseEntity.ok(response);
  } catch (DataIntegrityViolationException ex) {
   response.put("status", false);
   response.put("error", "Constraint violation: user_id may not exist");
   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  } catch (Exception ex) {
   response.put("status", false);
   response.put("error", "Unexpected error occurred");
   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
 }

 @PostMapping("/get")
 public ResponseEntity<Map<String, Object>> getTasksByUserId(@RequestBody Map<String, Object> request) {
  Map<String, Object> response = new HashMap<>();
  try {
   Long userId = Long.valueOf(request.get("user_id").toString());
   List<Task> tasks = taskService.getTasksByUserId(userId);
   response.put("status", true);
   response.put("data", tasks);
   return ResponseEntity.ok(response);
  } catch (Exception ex) {
   response.put("status", false);
   response.put("error", "Failed to fetch tasks");
   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
 }

 @PutMapping("/update")
public ResponseEntity<Map<String, Object>> updateStatus(@RequestBody Map<String, Object> request){
 Map<String, Object> response = new HashMap<>();
 try {
  List<Integer> taskIds = (List<Integer>) request.get("ids");
  int updatedCount = taskService.updateStatus(taskIds);
  response.put("status", true);
  response.put("msg", updatedCount + " task(s) updated successfully.");
  return ResponseEntity.ok(response);
 } catch (Exception ex) {
  response.put("status", false);
  response.put("error", "Failed to update tasks");
  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
 }
}


}
