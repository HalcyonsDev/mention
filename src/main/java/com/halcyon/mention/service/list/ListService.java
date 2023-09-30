package com.halcyon.mention.service.list;

import com.halcyon.mention.dto.list.NewListDto;
import com.halcyon.mention.model.TaskList;
import com.halcyon.mention.model.User;
import com.halcyon.mention.repository.IListRepository;
import com.halcyon.mention.service.auth.AuthService;
import com.halcyon.mention.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListService {
    private final IListRepository listRepository;
    private final UserService userService;
    private final AuthService authService;

    public TaskList create(NewListDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());

        TaskList list = new TaskList(dto.getTitle());
        list.setOwner(owner);

        return listRepository.save(list);
    }

    public String deleteById(Long id) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = findById(id);

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        listRepository.delete(list);
        return "List deleted successfully.";
    }

    public TaskList findById(Long id) {
        return listRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "List with this id not found."));
    }

    public List<TaskList> findAllByOwner() {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        return listRepository.findAllByOwner(owner);
    }

    public TaskList updateTitleById(Long id, String title) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = findById(id);

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        listRepository.updateTitleById(title, id);
        list.setTitle(title);

        return list;
    }

    public TaskList updateDescriptionById(Long id, String description) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        TaskList list = findById(id);

        if (!list.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this list.");
        }

        listRepository.updateDescriptionById(description, id);
        list.setDescription(description);

        return list;
    }
}