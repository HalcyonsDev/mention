package com.halcyon.mention.controller;

import com.halcyon.mention.dto.category.NewCategoryDto;
import com.halcyon.mention.model.Category;
import com.halcyon.mention.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody @Valid NewCategoryDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Category createdCategory = categoryService.create(dto);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable Long categoryId) {
        String response = categoryService.deleteById(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getById(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/list/{listId}")
    public ResponseEntity<List<Category>> getAllByListId(@PathVariable Long listId) {
        List<Category> listCategories = categoryService.findAllByListId(listId);
        return ResponseEntity.ok(listCategories);
    }
}