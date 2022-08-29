package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;


@Controller
@RequestMapping("admin")
public class testController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/")
    public String showAllUsers(Model model) {
        model.addAttribute("usersInController", userRepository.findAll());

        return "users-view";
    }

    @GetMapping("/{id}")
    public String findUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("userInController", userDao.findUser(id));
        return "show-view";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        model.addAttribute("addUserModel", new User());
        return "addUser-view";
    }

    @PostMapping("/")
    public String newUser(@ModelAttribute("xui") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        user.setRoles(roleRepository.findById(1L).stream().toList());
        userRepository.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/update")
    public String updateUser1(@PathVariable("id") Long id, Model model) {
        model.addAttribute("updateUser", userDao.findUser(id));
        return "updateUser-view";
    }

    @PatchMapping("/{id}")
    public String updateInfo(@ModelAttribute("updateInfo") User user, @PathVariable("id") int id) {
//        User updateUser = userDao.findUser(id);
        userDao.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userDao.deleteUser(id);
        return "redirect:/admin/";
    }
}
