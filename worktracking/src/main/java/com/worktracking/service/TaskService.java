package com.worktracking.service;

import com.worktracking.entity.Task;
import com.worktracking.entity.TaskStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
    List<Task> getAllTasks();
    Task getTaskById(Long id);
    void unassignTasksFromUser(Long userId);
    Task updateTaskStatus(Long id, TaskStatus taskStatus);
    List<Task> getTasksByStatus(TaskStatus status);
    List<Task> getTasksByUserId(Long userId);
    @Transactional
    void deleteUser(Long userId);
}
