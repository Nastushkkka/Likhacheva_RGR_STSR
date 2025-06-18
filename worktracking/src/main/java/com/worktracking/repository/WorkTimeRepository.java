package com.worktracking.repository;

import com.worktracking.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
    //  Получить рабочее время по пользователю
    List<WorkTime> findByUserId(Long userId);

    //  Получить рабочее время по задаче
    List<WorkTime> findByTaskId(Long taskId);

    //  Получить рабочее время по дате
    List<WorkTime> findByDateWorked(LocalDate dateWorked);

    @Query("SELECT w.status, COUNT(w) FROM WorkTime w GROUP BY w.status")
    List<Object[]> getWorkTimeStatistics();


}
