package com.halcyon.mention.repository;

import com.halcyon.mention.model.Category;
import com.halcyon.mention.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByList(TaskList list);
}