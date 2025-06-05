package com.todo.todo1_1.controller;

import com.todo.todo1_1.dto.UserDto;
import com.todo.todo1_1.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserDto userDto,
                                  BindingResult result) {
        logger.info("New user {}", userDto);

        if(result.hasErrors()) {
            return "register";
        }

        if(!userDto.getPassword().equals(userDto.getMatchingPassword())){
            result.rejectValue("password", "", "Password not matching");
            return "register";
        }

        userService.create(userDto);
        return "redirect:/login";
    }
}
