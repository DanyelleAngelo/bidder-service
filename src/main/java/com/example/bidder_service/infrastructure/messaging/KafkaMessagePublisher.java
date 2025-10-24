package com.example.bidder_service.infrastructure.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;
import com.example.bidder_service.domain.messaging.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class KafkaMessagePublisher implements MessagePublisher {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;
  private static final Logger logger = LoggerFactory.getLogger(KafkaMessagePublisher.class);

  @Override
  public void publish(String target, String message) throws InfrastructureException {
    logger.info("Sending message to topic {}...", target);
    try {
      CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(target, message);

      future.whenComplete((result, ex) -> {
        if( ex != null) {
          logger.error("Unable to send message to topic {} due to: {}", target, ex.getMessage());
        } else {
          logger.info("Sent message with offset=[{}] to topic {}", result.getRecordMetadata().offset(), target);
        }
      });
    } catch (Exception err) {
      String errorMessage = "An error occurred when trying to send message to " + target + " topic";
      throw new InfrastructureException(errorMessage, err);
    }
  }
}
