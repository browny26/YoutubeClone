package com.youtube.stage.controller;

import com.youtube.stage.model.User;
import com.youtube.stage.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe/{channelOwnerId}")
    public ResponseEntity<Void> subscribe(@PathVariable Long channelOwnerId, @AuthenticationPrincipal UserDetails userDetails) {
        subscriptionService.subscribe(channelOwnerId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe/{channelOwnerId}")
    public ResponseEntity<Void> unsubscribe(@PathVariable Long channelOwnerId, @AuthenticationPrincipal UserDetails userDetails) {
        subscriptionService.unsubscribe(channelOwnerId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-subscriptions")
    public ResponseEntity<List<User>> getMySubscriptions(@AuthenticationPrincipal UserDetails userDetails) {
        List<User> subscriptions = subscriptionService.getSubscriptionsForSubscriber(userDetails.getUsername());
        return ResponseEntity.ok(subscriptions);
    }

    @GetMapping("/channel/{channelOwnerId}/subscribers/count")
    public ResponseEntity<Long> getSubscriberCount(@PathVariable Long channelOwnerId) {
        long count = subscriptionService.countSubscribers(channelOwnerId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/channel/{channelOwnerId}/subscribed")
    public ResponseEntity<Boolean> isSubscribed(
            @PathVariable Long channelOwnerId,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean isSubscribed = subscriptionService.isSubscribed(userDetails.getUsername(), channelOwnerId);
        return ResponseEntity.ok(isSubscribed);
    }

}
