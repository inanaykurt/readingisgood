package com.retail.readingisgood.controller;

import com.retail.readingisgood.datatypes.request.OrdersInput;
import com.retail.readingisgood.errorhandler.InvalidOrderException;
import com.retail.readingisgood.errorhandler.ResourceNotFoundException;
import com.retail.readingisgood.model.Book;
import com.retail.readingisgood.model.Orders;
import com.retail.readingisgood.services.BookService;
import com.retail.readingisgood.services.CustomerService;
import com.retail.readingisgood.services.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Api(value="Order Service")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BookService bookService;

    @GetMapping("/order/{id}")
    private ResponseEntity<Orders> retriveOrderById(@PathVariable("id") long id) throws ResourceNotFoundException {
        return new ResponseEntity<Orders>(orderService.getOrderByOrderId(id), HttpStatus.OK);
    }

    @GetMapping("/startdate/{startDate}/enddate/{endDate}")
    private ResponseEntity<List<Orders>> listOrdersByDate(
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endDate) throws ResourceNotFoundException {

        return new ResponseEntity<>(
                orderService.listOrdersByDate(startDate,endDate), HttpStatus.OK);
    }

    @PostMapping("/save")
    private ResponseEntity<Orders> takeANewOrder(@RequestBody OrdersInput orderInput) throws ResourceNotFoundException, InvalidOrderException {
        Orders order = new Orders();

        order.setBookName(orderInput.getBookName());
        order.setOrderCount(orderInput.getOrderCount());
        order.setCustomerId(orderInput.getCustomerId());
        order.setOrderStatus("initiated");
        order.setOrderDate(new Date());

        if(orderInput.getOrderCount() <= 0){
            throw new InvalidOrderException("Order Count is invalid");
        }

        Book bookRecord = bookService.getBookByName(orderInput.getBookName());

        if(bookRecord == null || bookRecord.getBookCount() == 0){
              throw new ResourceNotFoundException("Book is not found in stock");
        }else if(bookRecord.getBookCount() - orderInput.getOrderCount() < 0 ){
              throw new ResourceNotFoundException("Book in stock is enough for order");
        }

        bookService.updateBookStock(bookRecord.getId(), orderInput.getOrderCount()*-1);

        order.setOrderStatus("completed");

        double amount = orderInput.getOrderCount() * bookRecord.getPrice();
        order.setOrderAmount(amount);
        orderService.saveOrder(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/update")
    private Orders updateOrder(@RequestBody Orders order)
    {
        return orderService.updateOrder(order);
    }




}
