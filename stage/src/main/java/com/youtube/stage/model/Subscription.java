package com.youtube.stage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "channel_owner_id")
    private User channelOwner;

    private LocalDateTime subscribedAt;

    public Subscription(Long id, User subscriber, User channelOwner, LocalDateTime subscribedAt) {
        this.id = id;
        this.subscriber = subscriber;
        this.channelOwner = channelOwner;
        this.subscribedAt = subscribedAt;
    }

    public Subscription() {

    }

    @PrePersist
    public void prePersist() {
        subscribedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getChannelOwner() {
        return channelOwner;
    }

    public void setChannelOwner(User channelOwner) {
        this.channelOwner = channelOwner;
    }

    public LocalDateTime getSubscribedAt() {
        return subscribedAt;
    }

    public void setSubscribedAt(LocalDateTime subscribedAt) {
        this.subscribedAt = subscribedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id) && Objects.equals(subscriber, that.subscriber) && Objects.equals(channelOwner, that.channelOwner) && Objects.equals(subscribedAt, that.subscribedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subscriber, channelOwner, subscribedAt);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", subscriber=" + subscriber +
                ", subscribedTo=" + channelOwner +
                ", subscribedAt=" + subscribedAt +
                '}';
    }
}
