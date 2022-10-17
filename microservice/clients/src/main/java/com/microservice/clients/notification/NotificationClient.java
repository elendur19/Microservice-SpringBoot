package com.microservice.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification")
public interface NotificationClient {

    @PostMapping(path = "api/v1/notification/{customerId}")
    NotificationResponse createNotification(@RequestBody NotificationRequest notificationRequest);

}