package com.example.bidder_service.infrastructure.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;
import com.example.bidder_service.domain.messaging.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessagePublisher implements MessagePublisher {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(String topic, String message) throws InfrastructureException {
    try {
      kafkaTemplate.send(topic, message);
    } catch (Exception err) {
      String errorMessage = String.format("An error occurred when trying to send message to %s topic", topic);
      throw new InfrastructureException(errorMessage, err);
    }
  }
}
