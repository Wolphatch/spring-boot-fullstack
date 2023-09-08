package com.zhiconghu.customer.request;

public record CustomerRegistrationRequest(String name, String email, Integer age, String gender) {
}
