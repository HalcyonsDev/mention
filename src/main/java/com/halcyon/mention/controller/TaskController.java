package com.halcyon.mention.controller;

import com.halcyon.mention.dto.task.NewTaskDto;
import com.halcyon.mention.model.Complexity;
import com.halcyon.mention.model.Status;
import com.halcyon.mention.model.Task;
import com.halcyon.mention.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(
            summary = "create task",
            description = "create task"
    )
    public ResponseEntity<Task> create(@RequestBody @Valid NewTaskDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Task createdTask = taskService.create(dto);
        return ResponseEntity.ok(createdTask);
    }

    @DeleteMapping("/delete/{taskId}")
    @Operation(
            summary = "find and delete task by its id",
            description = "delete task"
    )
    public ResponseEntity<String> delete(@PathVariable Long taskId) {
        String response = taskService.deleteById(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}")
    @Operation(
            summary = "find and return task by its id",
            description = "get task"
    )
    public ResponseEntity<Task> getById(@PathVariable Long taskId) {
        Task task = taskService.findById(taskId);
        System.out.println(task.getCreatedAt());
        return ResponseEntity.ok(task);
    }

    @GetMapping("/list/{listId}")
    @Operation(
            summary = "find and return all list's tasks",
            description = "get all tasks by list id"
    )
    public ResponseEntity<List<Task>> getAllByListId(@PathVariable Long listId) {
        List<Task> tasks = taskService.findAllByListId(listId);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/{taskId}/category/{categoryId}")
    @Operation(
            summary = "find and update task's category by its id",
            description = "update task's category"
    )
    public ResponseEntity<Task> updateCategory(@PathVariable Long taskId, @PathVariable Long categoryId) {
        Task updatedTask = taskService.updateCategoryById(taskId, categoryId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/update-title")
    @Operation(
            summary = "find and update task's title by its id",
            description = "update task's title"
    )
    public ResponseEntity<Task> updateTitle(
            @PathVariable Long taskId,
            @RequestParam
            @Size(min = 2, max = 50, message = "Title must be more than 1 character and less than 50 characters.") String value) {
        Task updatedTask = taskService.updateTitleById(taskId, value);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/update-description")
    @Operation(
            summary = "find and update task's description by its id",
            description = "update task's description"
    )
    public ResponseEntity<Task> updateDescription(
            @PathVariable Long taskId,
            @RequestParam
            @Size(min = 2, max = 100, message = "Description must be more than 1 character and less than 100 characters.") String value
    ) {
        Task updatedTask = taskService.updateDescriptionById(taskId, value);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/update-complexity")
    @Operation(
            summary = "find and update task's complexity by its id",
            description = "update task's complexity"
    )
    public ResponseEntity<Task> updateComplexity(
           @PathVariable Long taskId,
           @RequestParam @Valid Complexity value
    ) {
        Task updatedTask = taskService.updateComplexity(taskId, value);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/update-status")
    @Operation(
            summary = "find and update task's status by its id",
            description = "update task's status"
    )
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long taskId,
            @RequestParam @Valid Status value
            ) {
        Task updatedTask = taskService.updateStatusById(taskId, value);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/update-date")
    @Operation(
            summary = "find and update task's date its id",
            description = "update task's date"
    )
    public ResponseEntity<Task> updateDate(
            @PathVariable Long taskId,
            @RequestParam @Valid Instant value
    ) {
        Task updatedTask = taskService.updateDateById(taskId, value);
        return ResponseEntity.ok(updatedTask);
    }
}