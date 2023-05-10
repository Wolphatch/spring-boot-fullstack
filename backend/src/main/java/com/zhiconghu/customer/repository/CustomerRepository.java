package com.zhiconghu.customer.repository;

import com.zhiconghu.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    boolean existsCustomerByEmail(String email);


}
