package com.zhiconghu.customer.repository;

import com.zhiconghu.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJpaDataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerJpaDataAccessService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public boolean whetherEmailExist(String email){
        return customerRepository.existsCustomerByEmail(email);
    }

    @Override
    public boolean whetherIdExist(Integer id){
        return customerRepository.existsById(id);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customerRepository.deleteById(id);

    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }


}
