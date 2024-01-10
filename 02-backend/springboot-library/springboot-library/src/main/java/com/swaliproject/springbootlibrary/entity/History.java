package com.swaliproject.springbootlibrary.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="history")
public class History {

    public History(){}

    public History(String userEmail , String checkoutDate , String returnDate , String title , String author , String description , String img){
        this.userEmail =userEmail;
        this.checkoutDate = checkoutDate ;
        this.returnDate = returnDate;
        this.title= title;
        this.author=author;
        this.description= description;
        this.img=img;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;


    @Column(name = "returned_date")
    private String returnDate;

    private String title;

    private String author;

    private String description;

    private String img;
}
