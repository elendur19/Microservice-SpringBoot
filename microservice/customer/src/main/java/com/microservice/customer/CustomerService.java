package com.microservice.customer;

import com.microservice.amqp.RabbitMQMessageProducer;
import com.microservice.clients.fraud.FraudCheckResponse;
import com.microservice.clients.fraud.FraudClient;
import com.microservice.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository,
                              FraudClient fraudClient,
                              RabbitMQMessageProducer producer) {
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


        NotificationRequest notificationRequest =
                new NotificationRequest(customer.getId(),
                        customer.getEmail(),
                        "notification message");

        producer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );

        customerRepository.save(customer);
    }
}
