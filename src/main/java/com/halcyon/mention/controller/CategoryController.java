package com.halcyon.mention.controller;

import com.halcyon.mention.dto.category.NewCategoryDto;
import com.halcyon.mention.model.Category;
import com.halcyon.mention.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categories")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @Operation(
            summary = "create category",
            description = "create category"
    )
    public ResponseEntity<Category> create(@RequestBody @Valid NewCategoryDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Category createdCategory = categoryService.create(dto);
        return ResponseEntity.ok(createdCategory);
    }

    @DeleteMapping("/delete/{categoryId}")
    @Operation(
            summary = "find and delete category by its id",
            description = "delete category"
    )
    public ResponseEntity<String> delete(@PathVariable Long categoryId) {
        String response = categoryService.deleteById(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    @Operation(
            summary = "find and return category by his id",
            description = "get category"
    )
    public ResponseEntity<Category> getById(@PathVariable Long categoryId) {
        Category category = categoryService.findById(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/list/{listId}")
    @Operation(
            summary = "find and return all list's categories by its id",
            description = "get all categories by list id"
    )
    public ResponseEntity<List<Category>> getAllByListId(@PathVariable Long listId) {
        List<Category> listCategories = categoryService.findAllByListId(listId);
        return ResponseEntity.ok(listCategories);
    }
}