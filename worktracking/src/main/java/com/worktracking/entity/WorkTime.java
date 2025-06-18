package com.worktracking.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "work_time")
public class WorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private double hoursWorked;

    @Column(nullable = false)
    private LocalDate dateWorked = LocalDate.now();

    @Column(updatable = false, nullable = false)
    private final LocalDate createdAt = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WorkTimeStatus status = WorkTimeStatus.PENDING;

    public WorkTime() {}

    public WorkTime(User user, Task task, double hoursWorked) {
        this.user = user;
        this.task = task;
        this.hoursWorked = hoursWorked;
        System.out.println("Создан объект WorkTime с параметрами");
    }

    @PostLoad
    protected void postLoad() {
        System.out.println("Дата создания записи WorkTime: " + getCreatedAt());
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() {
        System.out.println("Метод getUser() вызван");
        return user;
    }

    public void setUser(User user) {
        System.out.println("Метод setUser() вызван");
        this.user = user;
    }

    public Task getTask() {
        System.out.println("Метод getTask() вызван");
        return task;
    }

    public void setTask(Task task) {
        System.out.println("Метод setTask() вызван");
        this.task = task;
    }

    public double getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(double hoursWorked) { this.hoursWorked = hoursWorked; }

    public LocalDate getDateWorked() { return dateWorked; }
    public void setDateWorked(LocalDate dateWorked) { this.dateWorked = dateWorked; }

    public LocalDate getCreatedAt() { return createdAt; }

    public WorkTimeStatus getStatus() { return status; }
    public void setStatus(WorkTimeStatus status) { this.status = status; }
}
