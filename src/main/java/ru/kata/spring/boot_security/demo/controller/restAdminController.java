package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class restAdminController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public restAdminController(RoleRepository roleRepository, UserRepository userRepository, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<List<User>> showAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping()
    public ResponseEntity<User> addUser (@RequestBody User user) {
        System.out.println("Save to base");
        System.out.println("NEW USER ---------------" + user);
        return ResponseEntity.ok(userRepository.save(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") long id) {
        return ResponseEntity.ok(userRepository.findById(id).orElseThrow());
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") long id) {
        System.out.println("USER DELETE ---------------" + userDao.findUser(id));
        userRepository.delete(userDao.findUser(id));
    }

    @PatchMapping("/{id}")
    public void updateUser(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("USER UPDATE ---------------" + user);
        userDao.updateUser(user);
    }
}
