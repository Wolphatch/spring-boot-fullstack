package com.zhiconghu.customer.service;

import com.zhiconghu.customer.model.Customer;
import com.zhiconghu.customer.repository.CustomerDao;
import com.zhiconghu.customer.request.CustomerRegistrationRequest;
import com.zhiconghu.customer.request.CustomerUpdateRequest;
import com.zhiconghu.exception.DuplicatedResourceException;
import com.zhiconghu.exception.RequestValidationException;
import com.zhiconghu.exception.ResourceNotFound;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {


    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao){
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer selectCustomerById(Integer id){

        return customerDao.selectCustomerById(id)
                .orElseThrow(()-> new ResourceNotFound("Customer [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        //check whether email exists
        if (customerDao.whetherEmailExist(customerRegistrationRequest.email())){
            throw new DuplicatedResourceException("Email has already been taken");
        }
        customerDao.insertCustomer(
                new Customer(
                        customerRegistrationRequest.name(),
                        customerRegistrationRequest.email(),
                        customerRegistrationRequest.age()
                )
        );
    }

    public void updateCustomer(Integer id, CustomerUpdateRequest customerUpdateRequest){
        if (!customerDao.whetherIdExist(id)){
            throw new ResourceNotFound("Customer not found");
        }
        Customer customer = selectCustomerById(id);

        boolean changes = false;

        if (customerUpdateRequest.name()!=null && !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes = true;
        }

        if (customerUpdateRequest.email()!=null && !customerUpdateRequest.email().equals(customer.getEmail())){
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }

        if (customerUpdateRequest.age()!=null && !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if(!changes){
            throw new RequestValidationException("no data changes found");
        }


        customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(Integer id){
        customerDao.deleteCustomerById(id);
    }
}
