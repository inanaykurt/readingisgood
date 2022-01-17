package com.retail.readingisgood.repository;

import com.retail.readingisgood.model.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends PagingAndSortingRepository<Orders, Long> {

    List<Orders> findAllByCustomerId(long customerId, Pageable pageable);

    @Query(value = "SELECT * FROM ORDERS O WHERE O.ORDER_DATE BETWEEN " +
            ":startDateTime AND :endDateTime",
            nativeQuery = true )
    List<Orders> findAllWithinTimeInterval(
            @Param("startDateTime") Date startDateTime,
            @Param("endDateTime") Date endDateTime);

    @Query(value = "SELECT * FROM ORDERS O WHERE O.CUSTOMER_ID =:customerId",
            nativeQuery = true )
    List<Orders> getAllByCustomerId(long customerId);
}
