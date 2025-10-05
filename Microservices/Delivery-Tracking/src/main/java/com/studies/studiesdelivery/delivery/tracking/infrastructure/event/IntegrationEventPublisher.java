package com.studies.studiesdelivery.delivery.tracking.infrastructure.event;

public interface IntegrationEventPublisher {

    void publish(Object event, String key, String topic);
}
