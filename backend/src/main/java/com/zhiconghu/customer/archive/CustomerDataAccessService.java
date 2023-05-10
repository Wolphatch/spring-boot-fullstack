package com.zhiconghu.customer.archive;

import com.zhiconghu.customer.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("list")
public class CustomerDataAccessService {
    private static List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                21

        );

        Customer jamila = new Customer(
                2,
                "Jamila",
                "jamila@gmail.com",
                21

        );

        customers.add(alex);
        customers.add(jamila);
    }

//    @Override
//    public List<Customer> selectAllCustomers(){
//       return customers;
//    }
//
//    @Override
//    public Optional<Customer> selectCustomerById(Integer id){
//        return customers
//                .stream()
//                .filter(c-> Objects.equals(c.getId(),id))
//                .findFirst();
//    }

}
