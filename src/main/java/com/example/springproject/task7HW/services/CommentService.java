package com.example.springproject.task7HW.services;

import com.example.springproject.task7HW.entities.Comments;
import com.example.springproject.task7HW.entities.Roles;
import com.example.springproject.task7HW.entities.ShopItems;
import com.example.springproject.task7HW.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CommentService {
    List<Comments> getAllComments();
    List<Comments> getAllCommentsByItem(ShopItems items);
    Comments saveComment(Comments comment);
    Comments getComment(Long id);
    void deleteComment(Comments comment);
    Comments addComments(Comments comment);
}
