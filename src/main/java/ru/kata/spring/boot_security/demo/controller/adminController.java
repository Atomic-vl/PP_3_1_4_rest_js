package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;


@Controller
@RequestMapping("/api/admin")
public class adminController {

    private UserDao userDao;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public adminController(UserDao userDao, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/")
    public String showAllUsers(Model model, Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("authorizedUser", user);
        model.addAttribute("usersInController", userRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "adminPage-view";
    }

    @PostMapping("/")
    public String newUser(@ModelAttribute("xui") User user, @RequestParam("rolesSet") Set<Role> rolesSet) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setRoles(Collections.singleton(new Role(1L,"ROLE_USER")));
//        user.setRoles(roleRepository.findById(1L).stream().toList());
        user.setRoles(rolesSet);
        userRepository.save(user);
        return "redirect:/api/admin/";
    }

    @PatchMapping("/update/{id}")
    public String updateInfo(@ModelAttribute("updateInfo") User user, @RequestParam("rolesSet") Set<Role> rolesSet) {
//        User updateUser = userDao.findUser(id);
        user.setRoles(rolesSet);
        userDao.updateUser(user);
        return "redirect:/api/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userDao.deleteUser(id);
        return "redirect:/api/admin/";
    }
}
//    @GetMapping("/{id}")
//    public String findUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("userInController", userDao.findUser(id));
//        return "show-view";
//    }
//    @GetMapping("/addUser")
//    public String addUser(Model model) {
//        model.addAttribute("addUserModel", new User());
//        return "addUser-view";
//    }
//    @GetMapping("/{id}/update")
//    public String updateUser1(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("updateUser", userDao.findUser(id));
//        return "updateUser-view";
//    }