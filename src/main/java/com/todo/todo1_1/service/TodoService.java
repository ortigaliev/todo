package com.todo.todo1_1.service;

import com.todo.todo1_1.Entity.Todo;
import com.todo.todo1_1.Entity.User;
import com.todo.todo1_1.dto.TodoDto;
import com.todo.todo1_1.repo.TodoRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TodoService {

    private TodoRepo todoRepo;

    private UserService userService;

    @Autowired
    public TodoService(TodoRepo todoRepo, UserService userService){
        this.todoRepo = todoRepo;
        this.userService = userService;
    }

    public Optional<TodoDto> findById(Long id){
        return todoRepo.findById(id).map(TodoDto::new);
    }

    public List<TodoDto> findTodosByUserId(Long userId) {
        return todoRepo.findTodosByUserId(userId);
    }

    public void save(TodoDto todoDto) {
        Optional<String> currentUserOptional = userService.getCurrentUser();
        if (currentUserOptional.isPresent()) {
            Optional<User> userOptional = userService.getByUsername(currentUserOptional.get());
            if (userOptional.isPresent()) {
                Todo todo = new Todo();
                todo.setId(todoDto.getId());
                todo.setDescription(todoDto.getDescription());
                todo.setTargetDate(todoDto.getTargetDate());
                todo.setUser(userOptional.get());
                todoRepo.save(todo);
            }
        }
    }

    public void delete(Long id) {
        todoRepo.findById(id)
                .ifPresent(todo -> todoRepo.delete(todo));
    }
}
