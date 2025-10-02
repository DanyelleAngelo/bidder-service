package com.example.bidder_service.infrastructure.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KafkaMessagePublisherTest {
  @InjectMocks
  KafkaMessagePublisher publisher;
  @Mock
  KafkaTemplate<String, String> kafkaTemplate;
  private final String message = "test message";
  private final String topic = "topic-name";

  @Test
  void shouldSendAMessageToTopic() throws InfrastructureException {
    publisher.publish(topic, message);

    verify(kafkaTemplate, times(1)).send(topic, message);
  }

  @Test
  void shouldThrowInfrastructureExceptionWhenErrorOccurs() {
    when(kafkaTemplate.send(topic, message)).thenThrow(new RuntimeException("Kafka failure"));

    assertThrows(InfrastructureException.class, () -> publisher.publish(topic, message));
  }
}
