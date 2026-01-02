package org.example;

import java.util.List;
import java.util.Optional;

public class UserService {
    UserDao userDao;

    public UserService() {
        this.userDao = new UserDaoImpl();
    }


    public User addUser(User user) {
        return userDao.create(user);
    }

    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User updateUser(User user) {
        return userDao.update(user);
    }

    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
