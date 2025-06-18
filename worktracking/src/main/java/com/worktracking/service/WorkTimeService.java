package com.worktracking.service;

import com.worktracking.entity.WorkTime;
import java.time.LocalDate;
import java.util.List;


public interface WorkTimeService {

    WorkTime createWorkTime(WorkTime workTime);
    WorkTime updateWorkTime(Long id, WorkTime workTime);
    void deleteWorkTime(Long id);
    List<WorkTime> getAllWorkTimes();
    WorkTime getWorkTimeById(Long id);
    List<WorkTime> getWorkTimesByUserId(Long userId);
    List<WorkTime> getWorkTimesByTaskId(Long taskId);
    List<WorkTime> getWorkTimesByDate(LocalDate date);
    WorkTime saveWorkTime(WorkTime workTime);
    WorkTime confirmWorkTime(Long id);
    public List<Object[]> getWorkTimeStatistics();


}
