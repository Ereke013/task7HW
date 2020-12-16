package com.example.springproject.task7HW.services.impl;

import com.example.springproject.task7HW.entities.Comments;
import com.example.springproject.task7HW.entities.Roles;
import com.example.springproject.task7HW.entities.ShopItems;
import com.example.springproject.task7HW.entities.Users;
import com.example.springproject.task7HW.repositories.CommentRepository;
import com.example.springproject.task7HW.repositories.RoleRepository;
import com.example.springproject.task7HW.repositories.UserRepository;
import com.example.springproject.task7HW.services.CommentService;
import com.example.springproject.task7HW.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comments> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comments saveComment(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comments getComment(Long id) {
        return commentRepository.getOne(id);
    }

    @Override
    public void deleteComment(Comments comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comments addComments(Comments comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comments> getAllCommentsByItem(ShopItems items) {
        return commentRepository.getAllByItems(items);
    }
}
