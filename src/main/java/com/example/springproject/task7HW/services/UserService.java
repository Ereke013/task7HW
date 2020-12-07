package com.example.springproject.task7HW.services;

import com.example.springproject.task7HW.entities.Categories;
import com.example.springproject.task7HW.entities.Roles;
import com.example.springproject.task7HW.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String email);

    Users createUser(Users user);

    List<Users> getAllUsers();
    Users saveUser(Users user);
    Users getUsers(Long id);
    void deleteUsers(Users user);
    Users addUser(Users user);

    List<Roles> getAllRoles();
    Roles getRoles(Long id);
    Roles createRoles(Roles roles);
    Roles saveRole(Roles role);
    void deleteRole(Roles role);

    Users updatePassword(Users user);
}
