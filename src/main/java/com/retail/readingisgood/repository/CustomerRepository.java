package com.retail.readingisgood.repository;

import com.retail.readingisgood.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    @Modifying
    @Query(
            value =
                    "insert into CUSTOMER (name, surname) values (:name, :surname)",
            nativeQuery = true)
    void createCustomer(@Param("name") String name, @Param("surname") String surname);

    @Query(
            value =
                    "select * from CUSTOMER where email = :email",
            nativeQuery = true)
    Optional<Customer> findByEmail(String email);
}
