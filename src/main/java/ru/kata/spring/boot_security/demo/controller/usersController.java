package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/api/users")
public class usersController {

    private UserDao userDao;
    private UserRepository userRepository;
    @Autowired
    public usersController(UserDao userDao, UserRepository userRepository) {
        this.userDao = userDao;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {
//        model.addAttribute("userInController", userDao.findUser(id));
//        model.addAttribute("userInController", userRepository.findById(id).get());
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("userInController", user);

        return "userPage-view";
    }

}
