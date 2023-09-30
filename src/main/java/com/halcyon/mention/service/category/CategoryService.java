package com.halcyon.mention.service.category;

import com.halcyon.mention.dto.category.NewCategoryDto;
import com.halcyon.mention.model.Category;
import com.halcyon.mention.model.TaskList;
import com.halcyon.mention.model.User;
import com.halcyon.mention.repository.ICategoryRepository;
import com.halcyon.mention.service.auth.AuthService;
import com.halcyon.mention.service.list.ListService;
import com.halcyon.mention.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    private final ListService listService;
    private final UserService userService;
    private final AuthService authService;

    public Category create(NewCategoryDto dto) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = listService.findById(dto.getListId());

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        Category category = new Category(dto.getTitle());
        category.setList(list);

        return categoryRepository.save(category);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Category category = findById(id);

        if (!category.getList().getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        categoryRepository.delete(category);
        return "Category deleted successfully.";
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with this id not found."));
    }

    public List<Category> findAllByListId(Long listId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = listService.findById(listId);

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        return categoryRepository.findAllByList(list);
    }
}