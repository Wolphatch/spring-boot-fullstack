package com.zhiconghu.customer.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "customer_email_unique",
                        columnNames = "email"
                )
        }
)
public class Customer {
    @Id
    @SequenceGenerator(
            name="customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String email;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private  Integer age;

    @Column(
            nullable = true
    )
    private String gender;

    public Customer(Integer id, String name, String email,  Integer age, String gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Customer(Integer id, String name, String email,  Integer age) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public Customer( String name, String email,  Integer age) {
        this.email = email;
        this.name = name;
        this.age = age;
    }

    public Customer( String name, String email,  Integer age, String gender) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Customer(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(age, customer.age) &&
                Objects.equals(gender, customer.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, age, gender);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", gender=" + gender + '\'' +
                '}';
    }
}
