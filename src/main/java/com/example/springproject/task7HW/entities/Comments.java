package com.example.springproject.task7HW.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "addDate")
    private Date addedDate;

    @ManyToOne
    private ShopItems items;

    @ManyToOne(fetch = FetchType.EAGER)
    private Users author;
}
