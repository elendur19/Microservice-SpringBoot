package com.microservice.customer;

import com.microservice.clients.fraud.FraudCheckResponse;
import com.microservice.clients.fraud.FraudClient;
import com.microservice.clients.notification.NotificationClient;
import com.microservice.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: make it async. i.e add it to queue
        notificationClient.createNotification(
                new NotificationRequest(customer.getId(),
                        customer.getEmail(),
                        "notification message"));

        customerRepository.save(customer);


    }
}
