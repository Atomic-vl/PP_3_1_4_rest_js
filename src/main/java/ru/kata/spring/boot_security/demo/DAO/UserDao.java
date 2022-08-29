package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public void deleteUser(Long id);

    public User findUser(Long id);

    public void updateUser(User user);

    public List<User> showAllUsers();

}
