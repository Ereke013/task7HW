package com.example.springproject.task7HW.repositories;

import com.example.springproject.task7HW.entities.Comments;
import com.example.springproject.task7HW.entities.ShopItems;
import com.example.springproject.task7HW.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comments,Long> {
    List<Comments> getAllByItems(ShopItems items);
}
