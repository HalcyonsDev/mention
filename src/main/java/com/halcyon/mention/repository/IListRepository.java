package com.halcyon.mention.repository;

import com.halcyon.mention.model.TaskList;
import com.halcyon.mention.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IListRepository extends JpaRepository<TaskList, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE TaskList list SET list.title = ?1 WHERE list.id = ?2")
    void updateTitleById(String title, Long id);

    @Transactional
    @Modifying
    @Query("UPDATE TaskList list SET list.description = ?1 WHERE list.id = ?2")
    void updateDescriptionById(String description, Long id);

    List<TaskList> findAllByOwner(User owner);
}