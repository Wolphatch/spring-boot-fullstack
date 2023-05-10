package com.zhiconghu.customer.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJpaDataAccessServiceTest {

    private CustomerJpaDataAccessService underTest;

    @Mock
    private CustomerRepository customerRepository;

    private AutoCloseable autoCloseable;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJpaDataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        // Given
        underTest.selectAllCustomers();


        // When

        // Then
       verify(customerRepository)
               .findAll();
    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 1;
        underTest.selectCustomerById(id);


        // When

        // Then
        verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        // Given


        // When

        // Then
    }

    @Test
    void whetherEmailExist() {
        // Given


        // When

        // Then
    }

    @Test
    void whetherIdExist() {
        // Given


        // When

        // Then
    }

    @Test
    void deleteCustomerById() {
        // Given


        // When

        // Then
    }

    @Test
    void updateCustomer() {
        // Given


        // When

        // Then
    }
}