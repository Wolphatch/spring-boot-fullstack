package com.zhiconghu.customer.repository;


import com.zhiconghu.AbstractTestContainers;
import com.zhiconghu.customer.mapper.CustomerRowMapper;
import com.zhiconghu.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class CustomerJdbcDataAccessServiceTest extends AbstractTestContainers {

    private CustomerJdbcDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new CustomerJdbcDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(customer);

        // When
       List<Customer> customers = underTest.selectAllCustomers();

        // Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                20
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c-> Objects.equals(c.getEmail(), email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
        });

    }

    @Test
    void willReturnEmptyWhenSelectCustomerById(){
        Integer id = -1;

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertNull(actual.orElse(null));
    }


    @Test
    void deleteCustomerById() {
        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c->Objects.equals(c.getEmail(),email))
                .findFirst()
                .map(Customer::getId)
                .orElse(null);

        // When
        underTest.deleteCustomerById(id);

        // Then
        assertThat(underTest.selectCustomerById(id)).isEmpty();
    }

    @Test
    void updateCustomer() {
        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);

        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c->Objects.equals(c.getEmail(),email))
                .findFirst()
                .map(Customer::getId)
                .orElse(null);

        // When
        String updatedEmail = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer updatedCustomer = new Customer(
                id,
                faker.name().fullName(),
                email,
                23
        );
        underTest.updateCustomer(updatedCustomer);

        // Then
        Optional<Customer> customerOptional = underTest.selectCustomerById(id);
        assertThat(customerOptional).isPresent().hasValueSatisfying(c->{
            assertThat(c.getName()).isEqualTo(updatedCustomer.getName());
            assertThat(c.getAge()).isEqualTo(updatedCustomer.getAge());
            assertThat(c.getEmail()).isEqualTo(updatedCustomer.getEmail());
        });
    }

    @Test
    void whetherEmailExist() {
        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                21
        );


        // When
        underTest.insertCustomer(customer);
        boolean whetherExist = underTest.whetherEmailExist(email);

        // Then
        assertThat(whetherExist).isTrue();

    }

    @Test
    void whetherIdExist() {

        // Given
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                faker.name().fullName(),
                email,
                21
        );

        underTest.insertCustomer(customer);
        Integer id = underTest.selectAllCustomers()
                .stream()
                .filter(c->Objects.equals(c.getEmail(),email))
                .findFirst()
                .map(Customer::getId)
                .orElse(null);


        // When

        boolean whetherExist = underTest.whetherIdExist(id);

        // Then
        assertThat(whetherExist).isTrue();
    }
}