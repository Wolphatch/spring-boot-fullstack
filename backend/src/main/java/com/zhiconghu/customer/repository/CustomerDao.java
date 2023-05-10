package com.zhiconghu.customer.repository;

import com.zhiconghu.customer.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);

    void insertCustomer(Customer customer);

    void deleteCustomerById(Integer id);

    void updateCustomer(Customer customer);

    boolean whetherEmailExist(String email);

    boolean whetherIdExist(Integer id);
}
