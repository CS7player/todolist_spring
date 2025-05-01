package com.p001.todolist.repository;

import com.p001.todolist.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    // No need to write custom INSERT query.
    // Use save() method provided by CrudRepository.
}
