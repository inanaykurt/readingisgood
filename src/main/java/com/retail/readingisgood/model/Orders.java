package com.retail.readingisgood.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String bookName;

    private Long customerId;

    private Long orderCount;

    private String orderStatus;

    private double orderAmount;

    private Date orderDate;

}
