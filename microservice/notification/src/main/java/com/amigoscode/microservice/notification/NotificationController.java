package com.amigoscode.microservice.notification;

import com.amigoscode.microservice.clients.notification.NotificationRequest;
import com.amigoscode.microservice.clients.notification.NotificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping()
    public NotificationResponse createNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("New notification... {}", notificationRequest);
        return notificationService.createCustomerNotification(notificationRequest);
    }
}
