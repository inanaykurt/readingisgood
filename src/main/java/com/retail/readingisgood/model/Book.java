package com.retail.readingisgood.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Data
@Table(name="BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="name")
    @NotBlank(message = "bookname is mandatory")
    private String name;

    @Column(name="author")
    @NotBlank(message = "author is mandatory")
    private String author;

    @Column(name="price")
    private Double price;

    @Column(name="bookCount")
    private Long bookCount;

    @Version
    private int version;

}
