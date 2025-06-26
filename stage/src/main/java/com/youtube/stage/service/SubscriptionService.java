package com.youtube.stage.service;

import com.youtube.stage.model.Subscription;
import com.youtube.stage.model.User;
import com.youtube.stage.repository.SubscriptionRepository;
import com.youtube.stage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public long countSubscribers(Long channelOwnerId) {
        return subscriptionRepository.countByChannelOwnerId(channelOwnerId);
    }


    public void subscribe(Long channelOwnerId, String subscriberEmail) {
        User subscriber = userRepository.findByEmail(subscriberEmail)
                .orElseThrow(() -> new RuntimeException("Subscriber user not found"));
        User channelOwner = userRepository.findById(channelOwnerId)
                .orElseThrow(() -> new RuntimeException("Channel owner user not found"));

        if (subscriptionRepository.existsBySubscriberAndChannelOwner(subscriber, channelOwner)) {
            throw new RuntimeException("Already subscribed");
        }

        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setChannelOwner(channelOwner);

        subscriptionRepository.save(subscription);
    }

    public void unsubscribe(Long channelOwnerId, String subscriberEmail) {
        User subscriber = userRepository.findByEmail(subscriberEmail)
                .orElseThrow(() -> new RuntimeException("Subscriber user not found"));
        User channelOwner = userRepository.findById(channelOwnerId)
                .orElseThrow(() -> new RuntimeException("Channel owner user not found"));

        Subscription subscription = subscriptionRepository.findBySubscriberAndChannelOwner(subscriber, channelOwner)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscriptionRepository.delete(subscription);
    }

    public List<User> getSubscriptionsForSubscriber(String email) {
        User subscriber = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return subscriptionRepository.findBySubscriber(subscriber).stream()
                .map(Subscription::getChannelOwner)
                .collect(Collectors.toList());
    }

    public List<User> getSubscribersByUser(Long channelOwnerId) {
        User owner = userRepository.findById(channelOwnerId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        return subscriptionRepository.findByChannelOwner(owner).stream()
                .map(Subscription::getSubscriber)
                .collect(Collectors.toList());
    }


    public List<User> getChannelsFollowedBy(User subscriber) {
        return subscriptionRepository.findBySubscriber(subscriber).stream()
                .map(Subscription::getChannelOwner)
                .collect(Collectors.toList());
    }

    public List<User> getSubscribersOf(User channelOwner) {
        return subscriptionRepository.findByChannelOwner(channelOwner).stream()
                .map(Subscription::getSubscriber)
                .collect(Collectors.toList());
    }

    public long getSubscriberCount(User channelOwner) {
        return subscriptionRepository.countByChannelOwner(channelOwner);
    }

    public long getSubscriptionsCount(User subscriber) {
        return subscriptionRepository.countBySubscriber(subscriber);
    }

    public boolean isSubscribed(String subscriberEmail, Long channelOwnerId) {
        User subscriber = userRepository.findByEmail(subscriberEmail)
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
        User channelOwner = userRepository.findById(channelOwnerId)
                .orElseThrow(() -> new RuntimeException("Channel owner not found"));

        return subscriptionRepository.findBySubscriberAndChannelOwner(subscriber, channelOwner).isPresent();
    }

}

