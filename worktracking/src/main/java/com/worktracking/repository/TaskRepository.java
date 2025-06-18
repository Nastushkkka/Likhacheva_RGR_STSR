package com.worktracking.repository;

import com.worktracking.entity.Task;
import com.worktracking.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Modifying
    @Query("UPDATE Task t SET t.assignedTo = NULL WHERE t.assignedTo.id = :userId")
    void updateAssignedToNull(Long userId);

    List<Task> findByStatus(TaskStatus status);
    List<Task> findByAssignedToId(Long userId);

}
