package com.zhiconghu.customer.controller;

import com.zhiconghu.customer.model.Customer;
import com.zhiconghu.customer.request.CustomerUpdateRequest;
import com.zhiconghu.customer.service.CustomerService;
import com.zhiconghu.customer.request.CustomerRegistrationRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }


    @RequestMapping(method = RequestMethod.GET,path = "{id}")
    public Customer getCustomer(@PathVariable("id") Integer id){
        return customerService
                .selectCustomerById(id);

    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest request){
        customerService.addCustomer(request);
    }

    @PutMapping("{id}")
    public void updateCustomer(@PathVariable("id") Integer id, @RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(id,customerUpdateRequest);
    }

    @DeleteMapping("{id}")
    public void deleteCustomer(@PathVariable("id") Integer id){
        customerService.deleteCustomer(id);
    }
}
