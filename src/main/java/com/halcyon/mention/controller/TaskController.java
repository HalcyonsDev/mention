package com.halcyon.mention.controller;

import com.halcyon.mention.dto.task.NewTaskDto;
import com.halcyon.mention.model.Complexity;
import com.halcyon.mention.model.Status;
import com.halcyon.mention.model.Task;
import com.halcyon.mention.service.task.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid NewTaskDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Task createdTask = taskService.create(dto);
        return ResponseEntity.ok(createdTask);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> delete(@PathVariable Long taskId) {
        String response = taskService.deleteById(taskId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getById(@PathVariable Long taskId) {
        Task task = taskService.findById(taskId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<List<Task>> getAllByListId(@PathVariable Long listId) {
        List<Task> tasks = taskService.findAllByListId(listId);
        return ResponseEntity.ok(tasks);
    }

    @PatchMapping("/update-title/{taskId}")
    public ResponseEntity<Task> updateTitle(
            @PathVariable Long taskId,
            @RequestParam
            @Size(min = 1, max = 50, message = "Title must be more than 1 character and less than 50 characters.") String title) {
        Task updatedTask = taskService.updateTitleById(taskId, title);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/update-description/{taskId}")
    public ResponseEntity<Task> updateDescription(
            @PathVariable Long taskId,
            @RequestParam
            @Size(min = 1, max = 100, message = "Title must be more than 1 character and less than 50 characters.") String description
    ) {
        Task updatedTask = taskService.updateDescriptionById(taskId, description);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/update-complexity/{taskId}")
    public ResponseEntity<Task> updateComplexity(
           @PathVariable Long taskId,
           @RequestParam @Valid Complexity complexity
    ) {
        Task updatedTask = taskService.updateComplexity(taskId, complexity);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/update-status/{taskId}")
    public ResponseEntity<Task> updateStatus(
            @PathVariable Long taskId,
            @RequestParam @Valid Status status
            ) {
        Task updatedTask = taskService.updateStatusById(taskId, status);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/update-date/{taskId}")
    public ResponseEntity<Task> updateDate(
            @PathVariable Long taskId,
            @RequestParam @Valid Instant date
    ) {
        Task updatedTask = taskService.updateDateById(taskId, date);
        return ResponseEntity.ok(updatedTask);
    }
}