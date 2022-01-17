package com.retail.readingisgood.controller;

import com.retail.readingisgood.datatypes.response.CustomerApiResponse;
import com.retail.readingisgood.errorhandler.ResourceExistsException;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Customer;
import com.retail.readingisgood.model.Orders;
import com.retail.readingisgood.services.CustomerService;
import com.retail.readingisgood.services.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@ControllerAdvice
@Api(value="Customer Service")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    private ResponseEntity<Customer> createNewCustomer(@Valid @RequestBody Customer customer) throws ResourceExistsException {
        Optional<Customer> customerInDB = customerService.retrieveCustomerByEmail(customer.getEmail());
        if(customerInDB.isPresent()){
            throw new ResourceExistsException("Customer Exists with given email:"+customer.getEmail());
        }
        Customer response = customerService.createCustomer(customer);
        return new ResponseEntity<Customer>(response, HttpStatus.CREATED);
    }

    @GetMapping("/retrieveOrders/{customerId}/page/{pageNo}/size/{pageSize}")
    private ResponseEntity<List<Orders>> getOrdersByCustomer(@RequestParam Integer customerId,
                                             @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                             @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                             @RequestParam(defaultValue = "id", required = false) String sortBy) throws Exception {

        boolean isCustomer = customerService.checkIsCustomer(customerId);

        if(!isCustomer){
            throw new ResourceNotFoundException("Customer Not Found");
        }

        List<Orders> response = orderService.getAllOrdersByCustomer(customerId, pageNo, pageSize, sortBy);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path="/retrieveCustomer/id/{id}")
    public CustomerApiResponse retrieveCustomer(@PathVariable long id) throws ResourceNotFoundException {
        CustomerApiResponse response = new CustomerApiResponse();
        Customer customer = customerService.retriveCustomer(id);

        if(customer == null){
            throw new ResourceNotFoundException("Customer Not Found");
        }

        response.setId(customer.getId());
        response.setSurname(customer.getSurname());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        return response;
    }


}
