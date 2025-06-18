package com.worktracking.service;

import com.worktracking.entity.Role;
import com.worktracking.entity.Task;
import com.worktracking.entity.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    void unassignTasksFromUser(Long userId);
    List<User> getUsersByRole(Role role);
    List<Task> getTasksByUserId(Long userId);
}
