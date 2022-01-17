package com.retail.readingisgood.services;

import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.model.Customer;
import com.retail.readingisgood.model.Orders;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    List<Orders> retrieveOrdersByCustomer(int no, int size, long customerId);

    boolean checkIsCustomer(long customerId);

    Customer retriveCustomer(long id);

    Optional<Customer> retrieveCustomerByEmail(String email);
}
