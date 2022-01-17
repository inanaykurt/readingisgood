package com.retail.readingisgood.services.impl;

import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Book;
import com.retail.readingisgood.model.Orders;
import com.retail.readingisgood.repository.OrderRepository;
import com.retail.readingisgood.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Orders> getAllOrdersByCustomer(long customerId,
                                             Integer pageNo,
                                             Integer pageSize,
                                             String sortBy) throws ResourceNotFoundException {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Orders> orders = orderRepository.findAllByCustomerId(customerId, paging);

        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Order is not found");
        }
        return orders;

    }

    @Override
    public Orders getOrderByOrderId(long id) throws ResourceNotFoundException {
        Optional<Orders> order = orderRepository.findById(id);

        if(order.isPresent()) {
            return order.get();
        } else {
            throw new ResourceNotFoundException("Order is not found");
        }
    }

    @Override
    public List<Orders> listOrdersByDate(Date startTime, Date endTime) throws ResourceNotFoundException {


        List<Orders> orders = orderRepository.findAllWithinTimeInterval(startTime, endTime);

        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Order is not found");
        }

        return orders;
    }

    @Override
    public Orders saveOrder(Orders order) {

        orderRepository.save(order);
        return order;
    }

    @Override
    public Orders updateOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Orders> findAllOrdersByCustomerId(long customerId){

        List<Orders> orders = orderRepository.getAllByCustomerId(customerId);

        return orders;
    }
}
