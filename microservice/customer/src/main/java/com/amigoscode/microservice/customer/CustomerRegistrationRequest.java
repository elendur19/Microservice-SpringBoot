package com.amigoscode.microservice.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
