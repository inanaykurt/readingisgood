package com.retail.readingisgood.datatypes.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStats {

    private String month;

    private long totalOrderCount;

    private long totalBookCount;

    private double totalPurchasedCount;

}
