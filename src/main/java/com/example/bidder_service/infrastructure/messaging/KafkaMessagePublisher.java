package com.example.bidder_service.infrastructure.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;
import com.example.bidder_service.domain.messaging.MessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaMessagePublisher implements MessagePublisher {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(String topic, String message) throws InfrastructureException {
    try {
      CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
      future.whenComplete((result, ex) -> {
        if (result != null) {
          System.out.println("Sent message with offset=[" + result.getRecordMetadata().offset() + "] to topic " + topic);
        } else {
          String errorMessage = String.format("Unable to send message to topic %s due to: %s", topic, ex.getMessage());
          System.out.println(errorMessage);
        }
      });
    } catch (Exception err) {
      String errorMessage = String.format("An error occurred when trying to send message to %s topic", topic);
      throw new InfrastructureException(errorMessage, err);
    }
  }
}
