package com.worktracking.service;

import com.worktracking.entity.WorkTime;
import com.worktracking.entity.WorkTimeStatus;
import com.worktracking.repository.WorkTimeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkTimeServiceImpl implements WorkTimeService {
    private final WorkTimeRepository workTimeRepository;

    public WorkTimeServiceImpl(WorkTimeRepository workTimeRepository) {
        this.workTimeRepository = workTimeRepository;
    }

    @Override
    public WorkTime createWorkTime(WorkTime workTime) {
        if (workTime.getUser() == null || workTime.getTask() == null) {
            throw new RuntimeException("Пользователь и задача должны быть указаны!");
        }

        if (workTime.getStatus() == null) {
            workTime.setStatus(WorkTimeStatus.PENDING);
        }

        return saveWorkTime(workTime);
    }


    @Override
    public WorkTime updateWorkTime(Long id, WorkTime updatedWorkTime) {
        WorkTime existingWorkTime = workTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));

        existingWorkTime.setHoursWorked(updatedWorkTime.getHoursWorked());
        existingWorkTime.setDateWorked(updatedWorkTime.getDateWorked());
        existingWorkTime.setStatus(updatedWorkTime.getStatus());
        existingWorkTime.setTask(updatedWorkTime.getTask());

        return workTimeRepository.save(existingWorkTime);
    }

    @Override
    public void deleteWorkTime(Long id) {
        workTimeRepository.deleteById(id);
    }

    @Override
    public List<WorkTime> getAllWorkTimes() {
        return workTimeRepository.findAll();
    }

    @Override
    public WorkTime getWorkTimeById(Long id) {
        return workTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
    }

    @Override
    @Transactional
    public WorkTime saveWorkTime(WorkTime workTime) {
        return workTimeRepository.save(workTime);
    }

    @Override
    public List<WorkTime> getWorkTimesByUserId(Long userId) {
        return workTimeRepository.findByUserId(userId);
    }

    @Override
    public List<WorkTime> getWorkTimesByTaskId(Long taskId) {
        return workTimeRepository.findByTaskId(taskId);
    }

    @Override
    public List<WorkTime> getWorkTimesByDate(LocalDate date) {
        return workTimeRepository.findByDateWorked(date);
    }

    @Override
    public WorkTime confirmWorkTime(Long id) {
        WorkTime workTime = workTimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));

        workTime.setStatus(WorkTimeStatus.CONFIRMED);
        return workTimeRepository.save(workTime);
    }

    @Override
    public List<Object[]> getWorkTimeStatistics() {
        return workTimeRepository.getWorkTimeStatistics();
    }

}
