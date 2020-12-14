package com.microservice.api.event;


import java.time.LocalDateTime;

/**
 * @param <K>
 * @param <T>
 */
public class Event<K, T> {

    public enum Type {CREATE,DELETE};

    private Event.Type eventType;
    private K key;
    private T data;
    private LocalDateTime eventCreatedAt;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = LocalDateTime.now();
    }

    public Type getEventType() {
        return eventType;
    }

    public void setEventType(Type eventType) {
        this.eventType = eventType;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public LocalDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }

    public void setEventCreatedAt(LocalDateTime eventCreatedAt) {
        this.eventCreatedAt = eventCreatedAt;
    }
}
