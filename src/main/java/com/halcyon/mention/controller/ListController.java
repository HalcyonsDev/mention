package com.halcyon.mention.controller;

import com.halcyon.mention.dto.list.NewListDto;
import com.halcyon.mention.model.TaskList;
import com.halcyon.mention.service.list.ListService;
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

import java.util.List;

@RestController
@RequestMapping("/api/lists")
@RequiredArgsConstructor
@Validated
@Tag(name = "Lists")
public class ListController {
    private final ListService listService;

    @PostMapping
    @Operation(
            summary = "create list",
            description = "create list"
    )
    public ResponseEntity<TaskList> create(@RequestBody @Valid NewListDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        TaskList createdList = listService.create(dto);
        return ResponseEntity.ok(createdList);
    }

    @DeleteMapping("/delete/{listId}")
    @Operation(
            summary = "find and delete list by its id",
            description = "delete list"
    )
    public ResponseEntity<String> delete(@PathVariable Long listId) {
        String response = listService.deleteById(listId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{listId}")
    @Operation(
            summary = "find and return list by its id",
            description = "get list"
    )
    public ResponseEntity<TaskList> getById(@PathVariable Long listId) {
        TaskList list = listService.findById(listId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/my")
    @Operation(
            summary = "find and return all user's lists",
            description = "get all lists by owner"
    )
    public ResponseEntity<List<TaskList>> getAllByOwner() {
        List<TaskList> lists = listService.findAllByOwner();
        return ResponseEntity.ok(lists);
    }

    @PatchMapping("/{listId}/update-title")
    @Operation(
            summary = "find and update list's title by its id",
            description = "update list's title"
    )
    public ResponseEntity<TaskList> updateTitle(
            @PathVariable Long listId,
            @RequestParam
            @Size(min = 2, max = 50, message = "Title must be more than 1 character and less than 50 characters.") String value
    ) {
        TaskList updatedList = listService.updateTitleById(listId, value);
        return ResponseEntity.ok(updatedList);
    }

    @PatchMapping("/{listId}/update-description")
    @Operation(
            summary = "find and update list's description by its id",
            description = "update list's description"
    )
    public ResponseEntity<TaskList> updateDescription(
            @PathVariable Long listId,
            @RequestParam
            @Size(min = 2, max = 100, message = "Description must be more than 1 character and less than 100 characters.") String value
    ) {
        TaskList updatedList = listService.updateDescriptionById(listId, value);
        return ResponseEntity.ok(updatedList);
    }
}