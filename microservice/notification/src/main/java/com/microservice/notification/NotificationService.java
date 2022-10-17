package com.microservice.notification;

import com.microservice.clients.notification.NotificationRequest;
import com.microservice.clients.notification.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse createCustomerNotification(NotificationRequest notificationRequest) {
        Notification notification = Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .message(notificationRequest.message())
                .toCustomerEmail(notificationRequest.toCustomerEmail())
                .sentAt(LocalDateTime.now())
                .sender("Customer Microservice")
                .build();

        notificationRepository.save(notification);

        return NotificationResponse.builder()
                .customerId(notification.getToCustomerId())
                .customerEmail(notification.getToCustomerEmail())
                .message(notification.getMessage())
                .build();

    }

}
