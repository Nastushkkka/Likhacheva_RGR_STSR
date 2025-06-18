package com.worktracking.service;

import com.worktracking.entity.Role;
import com.worktracking.entity.Task;
import com.worktracking.entity.User;
import com.worktracking.repository.TaskRepository;
import com.worktracking.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TaskRepository taskRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с именем " + user.getUsername() + " уже существует");
        }

        if (user.getRole() == null) {
            user = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), Role.GUEST);
        }

        User newUser = new User();
        System.out.println("Создан новый пользователь: " + newUser);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с id " + id + " не найден"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.setUsername(user.getUsername());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        existing.setRole(user.getRole());
        return userRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        unassignTasksFromUser(id);
        userRepository.deleteById(id);
    }



    @Override
    @Transactional
    public void unassignTasksFromUser(Long userId) {
        if (!getTasksByUserId(userId).isEmpty()) {
            taskRepository.updateAssignedToNull(userId);
        }
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<Task> getTasksByUserId(Long userId) {
        return taskRepository.findByAssignedToId(userId);
    }
}
