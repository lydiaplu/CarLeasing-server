package com.lydiaplullc.CarLeasing.service;

import com.lydiaplullc.CarLeasing.model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);

    List<User> getUsers();

    void deleteUser(String email);

    User getUser(String email);
}
