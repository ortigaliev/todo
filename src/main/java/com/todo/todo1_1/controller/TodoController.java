package com.todo.todo1_1.controller;

import com.todo.todo1_1.dto.TodoDto;
import com.todo.todo1_1.exceptions.ResourceNotFoundException;
import com.todo.todo1_1.service.TodoService;
import com.todo.todo1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TodoController {

    private TodoService todoService;

    private UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService){
        this.todoService = todoService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        List<TodoDto> todos = todoService.findTodosByUserId(
                userService.getCurrentUserId().orElseThrow(ResourceNotFoundException::new));
        model.addAttribute("todos", todos);
        return "index";
    }

    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model) {
        TodoDto todoDto = todoService.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", todoDto);
        return "todo";
    }

    @GetMapping("/todo/create")
    public String createTodoPage(Model model){
        model.addAttribute("todo", new TodoDto());
        return "todo";
    }

    @PostMapping("/todo/create")
    public String createTodoPost(@ModelAttribute("todo") TodoDto todo) {
        todoService.save(todo);
        return "redirect:/";
    }

    @GetMapping("/todo/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return "redirect:/";
    }


}
