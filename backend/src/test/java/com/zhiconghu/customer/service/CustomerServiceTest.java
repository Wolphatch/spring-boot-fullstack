package com.zhiconghu.customer.service;

import com.zhiconghu.customer.model.Customer;
import com.zhiconghu.customer.repository.CustomerDao;
import com.zhiconghu.customer.request.CustomerRegistrationRequest;
import com.zhiconghu.customer.request.CustomerUpdateRequest;
import com.zhiconghu.exception.DuplicatedResourceException;
import com.zhiconghu.exception.ResourceNotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    private CustomerDao customerDao;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }


    @Test
    void getAllCustomers() {

        // When
        underTest.getAllCustomers();

        // Then
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void selectCustomerById() {
        // Given
        int id = 10;

        Customer customer = new Customer(
                id,
                "alex",
                "ales@gmail.com",
                19
        );

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        // When
        Customer actual = underTest.selectCustomerById(10);

        // Then
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional(){
        // Given
        int id = 10;


        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(()->underTest.selectCustomerById(id))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessage(
                        "Customer [%s] not found".formatted(id)
                );
    }

    @Test
    void addCustomer() {
        // Given
        String email = "ales@gmail.com";

        when(customerDao.whetherEmailExist(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "alex",
                email,
                19
        );

        // When
        underTest.addCustomer(request);
        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer =  customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void failedToInsertWhenEmailExist(){
        String email = "ales@gmail.com";

        when(customerDao.whetherEmailExist(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "alex",
                email,
                12
        );

        assertThatThrownBy(()->underTest.addCustomer(request))
                .isInstanceOf(DuplicatedResourceException.class)
                .hasMessage("Email has already been taken");

    }

    @Test
    void updateCustomer() {
        // Given
        int id = 10;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "updated alex",
                "updated email",
                22
        );
        Customer customer = new Customer(
                id,
                "alex",
                "email",
                30
        );
        when(customerDao.whetherIdExist(id)).thenReturn(true);
        when(customerDao.selectCustomerById(10)).thenReturn(Optional.of(customer));
        // When
        underTest.updateCustomer(id,request);

        // Then
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer updatedCustomer = argumentCaptor.getValue();

        assertThat(updatedCustomer.getId()).isEqualTo(id);
        assertThat(updatedCustomer.getName()).isEqualTo(request.name());
        assertThat(updatedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(updatedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void updateIsFailedCustomerIsNotFound() {
        // Given
        int id = 10;
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "updated alex",
                "updated email",
                22
        );
        when(customerDao.whetherIdExist(id)).thenReturn(false);
        // When
        // Then
        assertThatThrownBy(()-> underTest.updateCustomer(id,request))
                .isInstanceOf(ResourceNotFound.class);
    }

    @Test
    void deleteCustomer() {
        // Given


        // When

        // Then
    }
}