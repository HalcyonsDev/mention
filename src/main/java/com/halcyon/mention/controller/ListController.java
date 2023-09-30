package com.halcyon.mention.controller;

import com.halcyon.mention.dto.list.NewListDto;
import com.halcyon.mention.model.TaskList;
import com.halcyon.mention.service.list.ListService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
public class ListController {
    private final ListService listService;

    @PostMapping
    public ResponseEntity<TaskList> create(@RequestBody @Valid NewListDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        TaskList createdList = listService.create(dto);
        return ResponseEntity.ok(createdList);
    }

    @DeleteMapping("/delete/{listId}")
    public ResponseEntity<String> delete(@PathVariable Long listId) {
        String response = listService.deleteById(listId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{listId}")
    public ResponseEntity<TaskList> getById(@PathVariable Long listId) {
        TaskList list = listService.findById(listId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TaskList>> getAllByOwner() {
        List<TaskList> lists = listService.findAllByOwner();
        return ResponseEntity.ok(lists);
    }

    @PatchMapping("/update-title/{listId}")
    public ResponseEntity<TaskList> updateTitle(@PathVariable Long listId, @RequestParam String title) {
        TaskList updatedList = listService.updateTitleById(listId, title);
        return ResponseEntity.ok(updatedList);
    }

    @PatchMapping("/update-description/{listId}")
    public ResponseEntity<TaskList> updateDescription(@PathVariable Long listId, @RequestParam String description) {
        TaskList updatedList = listService.updateDescriptionById(listId, description);
        return ResponseEntity.ok(updatedList);
    }
}