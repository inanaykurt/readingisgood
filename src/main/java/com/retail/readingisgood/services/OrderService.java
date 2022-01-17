package com.retail.readingisgood.services;

import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Orders;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Orders> getAllOrdersByCustomer(long customerId, Integer pageNo, Integer pageSize, String sortBy) throws ResourceNotFoundException;

    Orders getOrderByOrderId(long id) throws ResourceNotFoundException;

    List<Orders> listOrdersByDate(Date startTime, Date endTime) throws ResourceNotFoundException;

    Orders saveOrder(Orders order);

    Orders updateOrder(Orders order);

    List<Orders> findAllOrdersByCustomerId(long customerId);
}
