package com.example.springproject.task7HW.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_baskets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Baskets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dateOfBuy")
    private Date date;

    @Column(name = "Amount")
    private int amount;

    @ManyToOne(fetch = FetchType.EAGER)
    private ShopItems items;
}
