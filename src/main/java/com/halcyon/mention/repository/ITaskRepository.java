package com.halcyon.mention.repository;

import com.halcyon.mention.model.Task;
import com.halcyon.mention.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.title = ?1 WHERE task.id = ?2")
    void updateTitleById(String title, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.description = ?1 WHERE task.id = ?2")
    void updateDescriptionById(String description, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.complexity = ?1 WHERE task.id = ?2")
    void updateComplexityById(String complexity, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.status = ?1 WHERE task.id = ?2")
    void updateStatusById(String status, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Task task SET task.date = ?1 WHERE task.id = ?2")
    void updateDateById(Instant date, Long id);

    List<Task> findAllByList(TaskList list);
}