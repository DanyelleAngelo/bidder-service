package com.example.bidder_service.domain.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;

public interface MessagePublisher {
  void publish(String topic, String message) throws InfrastructureException;
}
