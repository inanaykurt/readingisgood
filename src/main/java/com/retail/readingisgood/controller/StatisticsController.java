package com.retail.readingisgood.controller;

import com.retail.readingisgood.datatypes.response.MonthlyStatsResponse;
import com.retail.readingisgood.datatypes.response.OrderStats;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Orders;
import com.retail.readingisgood.services.BookService;
import com.retail.readingisgood.services.CustomerService;
import com.retail.readingisgood.services.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Api(value="Statistics Service")
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    OrderService orderService;

    @GetMapping("/monthlyStats/custId/{customerId}")
    private MonthlyStatsResponse getMonthlyStats(@PathVariable("customerId") long customerId) throws ResourceNotFoundException {
        MonthlyStatsResponse response = new MonthlyStatsResponse();
        ArrayList<OrderStats> orderStats = new ArrayList<>();

        List<Orders> orders = orderService.findAllOrdersByCustomerId(customerId);

        if(!orders.isEmpty()){

            Map<String, List<Orders>> ordersByMonth = orders.stream()
                    .filter(p -> p.getOrderStatus().contentEquals("completed"))
                    .collect(Collectors.groupingBy(p -> formatDate(p.getOrderDate().getMonth())));

            ordersByMonth.entrySet().stream()
                    .forEach(p->{
                        Long totalBookCount = p.getValue().stream().collect(Collectors.summingLong(Orders::getOrderCount));
                        Double totalPurchasedAmount = p.getValue().stream().collect(Collectors.summingDouble(Orders::getOrderAmount));
                        Long totalOrderCount = p.getValue().stream().collect(Collectors.counting());

                        OrderStats orderStatByMonth = new OrderStats();
                        orderStatByMonth.setMonth(p.getKey());
                        orderStatByMonth.setTotalBookCount(totalBookCount);
                        orderStatByMonth.setTotalPurchasedCount(totalPurchasedAmount);
                        orderStatByMonth.setTotalOrderCount(totalOrderCount);
                        orderStats.add(orderStatByMonth);

                    });


            response.setOrderStats(orderStats);
        }else{
            throw new ResourceNotFoundException("No Order Found For This Customer");
        }

        return response;
    }

    private String formatDate(int month) {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMM", Locale.ENGLISH).format(cal.getTime());
    }


}
