package com.retail.readingisgood.services.impl;


import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.model.Customer;
import com.retail.readingisgood.model.Orders;
import com.retail.readingisgood.repository.CustomerRepository;
import com.retail.readingisgood.repository.OrderRepository;
import com.retail.readingisgood.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public List<Orders> retrieveOrdersByCustomer(int no, int size, long customerId) {

        Pageable pageable = PageRequest.of(no, size);
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer == null){
            return null;
        }
        List<Orders> orders = orderRepository.findAllByCustomerId(customer.get().getId(), pageable);
        return orders;
    }

    @Override
    public boolean checkIsCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if(customer.isPresent()){
            return true;
        }else{
            return false;
        }


    }

    @Override
    public Customer retriveCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        return customer.get();
    }

    @Override
    public Optional<Customer> retrieveCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepository.findByEmail(email);
        return customer;
    }


}
