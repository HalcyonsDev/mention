package com.halcyon.mention.service.task;

import com.halcyon.mention.dto.task.NewTaskDto;
import com.halcyon.mention.model.*;
import com.halcyon.mention.repository.ITaskRepository;
import com.halcyon.mention.service.auth.AuthService;
import com.halcyon.mention.service.list.ListService;
import com.halcyon.mention.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final ITaskRepository taskRepository;
    private final ListService listService;
    private final UserService userService;
    private final AuthService authService;

    public Task create(NewTaskDto dto) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = listService.findById(dto.getListId());

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        Task task = new Task(dto.getTitle());
        task.setList(list);

        return taskRepository.save(task);
    }

    public String deleteById(Long id) {
        Task task = checkUserForAccess(id);

        taskRepository.delete(task);
        return "Task deleted successfully.";
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task with this id not found."));
    }

    public List<Task> findAllByListId(Long listId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = listService.findById(listId);

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        return taskRepository.findAllByList(list);
    }

    public Task updateTitleById(Long id, String title) {
        Task task = checkUserForAccess(id);

        taskRepository.updateTitleById(title, id);
        task.setTitle(title);

        return task;
    }

    public Task updateDescriptionById(Long id, String description) {
        Task task = checkUserForAccess(id);

        taskRepository.updateDescriptionById(description, id);
        task.setDescription(description);

        return task;
    }

    public Task updateComplexity(Long id, Complexity complexity) {
        Task task = checkUserForAccess(id);

        taskRepository.updateComplexityById(complexity, id);
        task.setComplexity(complexity);

        return task;
    }

    public Task updateStatusById(Long id, Status status) {
        Task task = checkUserForAccess(id);

        taskRepository.updateStatusById(status, id);
        task.setStatus(status);

        return task;
    }

    public Task updateDateById(Long id, Instant date) {
        Task task = checkUserForAccess(id);

        taskRepository.updateDateById(date, id);
        task.setDate(date);

        return task;
    }

    private Task checkUserForAccess(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Task task = findById(id);

        if (!task.getList().getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        return task;
    }
}