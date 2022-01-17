package com.retail.readingisgood.datatypes.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersInput {

    @NotNull
    private String bookName;

    @NotNull
    private Long customerId;

    @NotNull
    private Long orderCount;

}
