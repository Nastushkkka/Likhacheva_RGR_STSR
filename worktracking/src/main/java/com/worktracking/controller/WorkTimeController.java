package com.worktracking.controller;

import com.worktracking.entity.WorkTime;
import com.worktracking.service.WorkTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/worktime")
public class WorkTimeController {

    private final WorkTimeService workTimeService;

    public WorkTimeController(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<WorkTime> createWorkTime(@RequestBody WorkTime workTime) {
        if (workTime.getUser() == null || workTime.getTask() == null) {
            return ResponseEntity.badRequest().build();
        }

        WorkTime savedWorkTime = workTimeService.createWorkTime(workTime);
        return ResponseEntity.status(201).body(savedWorkTime);
    }

    @GetMapping
    public ResponseEntity<List<WorkTime>> getAllWorkTimes() {
        return ResponseEntity.ok(workTimeService.getAllWorkTimes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkTime> getWorkTimeById(@PathVariable Long id) {
        WorkTime workTime = workTimeService.getWorkTimeById(id);

        System.out.println("Метод getUser() вызван: " + workTime.getUser());
        System.out.println("Метод getTask() вызван: " + workTime.getTask());

        return ResponseEntity.ok(workTime);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkTime(@PathVariable Long id) {
        workTimeService.deleteWorkTime(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkTime> updateWorkTime(@PathVariable Long id, @RequestBody WorkTime workTime) {
        WorkTime updatedWorkTime = workTimeService.updateWorkTime(id, workTime);
        return ResponseEntity.ok(updatedWorkTime);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkTime>> getWorkTimesByUser(@PathVariable Long userId) {
        List<WorkTime> workTimes = workTimeService.getWorkTimesByUserId(userId);
        return workTimes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workTimes);
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<List<WorkTime>> getWorkTimesByTask(@PathVariable Long taskId) {
        List<WorkTime> workTimes = workTimeService.getWorkTimesByTaskId(taskId);
        return workTimes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workTimes);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<WorkTime>> getWorkTimesByDate(@PathVariable LocalDate date) {
        List<WorkTime> workTimes = workTimeService.getWorkTimesByDate(date);
        return workTimes.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(workTimes);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<WorkTime> confirmWorkTime(@PathVariable Long id) {
        return ResponseEntity.ok(workTimeService.confirmWorkTime(id));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Object[]>> getWorkTimeStatistics() {
        List<Object[]> stats = workTimeService.getWorkTimeStatistics();
        return ResponseEntity.ok(stats);
    }


}
