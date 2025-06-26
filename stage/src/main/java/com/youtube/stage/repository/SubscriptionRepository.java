package com.youtube.stage.repository;

import com.youtube.stage.model.Subscription;
import com.youtube.stage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsBySubscriberAndChannelOwner(User subscriber, User channelOwner);
    List<Subscription> findBySubscriber(User subscriber);
    List<Subscription> findByChannelOwner(User channelOwner);

    Optional<Subscription> findBySubscriberAndChannelOwner(User subscriber, User channelOwner);

    long countByChannelOwner(User channelOwner);
    long countBySubscriber(User subscriber);

    long countByChannelOwnerId(Long channelOwnerId);
}
