package com.zhiconghu.customer.repository;

import com.zhiconghu.customer.mapper.CustomerRowMapper;
import com.zhiconghu.customer.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJdbcDataAccessService implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper;

    public CustomerJdbcDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }
    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, email, age FROM customer
                """;
        return  jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, email, age FROM customer where id = ?
                """;
        return jdbcTemplate.query(sql,customerRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                    INSERT INTO customer(name, email, age) VALUES (?, ?, ?)
                    """;
        int result = jdbcTemplate.update(
                sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
        System.out.println("jdbcTemplate.update = " + result);
    }

    @Override
    public void deleteCustomerById(Integer id) {
            var sql = """
                DELETE FROM customer where id = ?
                """;
            int result = jdbcTemplate.update(sql,id);
            System.out.println("delete customer by id result = "+result);
    }

    @Override
    public void updateCustomer(Customer customer) {
        if(customer.getName()!=null) {
            String sql = "UPDATE customer SET name = ? where id = ?";
            int result = jdbcTemplate.update(sql,customer.getName(),customer.getId());
            System.out.println("Update customer name result = "+result);
        }
        if(customer.getEmail()!=null) {
            String sql = "UPDATE customer SET email = ? where id = ?";
            int result = jdbcTemplate.update(sql,customer.getEmail(),customer.getId());
            System.out.println("Update customer email result = "+result);
        }
        if(customer.getAge()!=null) {
            String sql = "UPDATE customer SET age = ? where id = ?";
            int result = jdbcTemplate.update(sql,customer.getAge(),customer.getId());
            System.out.println("Update customer age result = "+result);

        }
    }

    @Override
    public boolean whetherEmailExist(String email) {
        var sql = """
                SELECT count(id) "countId" FROM customer where email = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,email);
        return count!=null && count>0;
    }

    @Override
    public boolean whetherIdExist(Integer id) {
        var sql = """
                SELECT count(id) "countId" FROM customer where id = ?
                """;
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count!=null && count>0;
    }
}
