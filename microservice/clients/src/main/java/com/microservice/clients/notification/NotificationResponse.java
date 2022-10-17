package com.microservice.clients.notification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class NotificationResponse {
    private Integer customerId;
    private String customerEmail;
    private String message;
}
