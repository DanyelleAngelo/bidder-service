package com.example.bidder_service.domain.messaging;

public interface MessagePublisher {
  void publish(String topic, String message);
}
