package com.example.bidder_service.infrastructure.messaging;

import com.example.bidder_service.domain.exception.InfrastructureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

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
    when(kafkaTemplate.send(topic, message)).thenReturn(CompletableFuture.completedFuture(null));

    publisher.publish(topic, message);

    verify(kafkaTemplate, times(1)).send(topic, message);
  }

  @Test
  void shouldThrowInfrastructureExceptionWhenSendThrowsImmediately() {
    when(kafkaTemplate.send(topic, message)).thenThrow(new RuntimeException("Kafka failure"));

    assertThrows(InfrastructureException.class, () -> publisher.publish(topic, message));
  }

//  @Test
//  void shouldThrowInfrastructureExceptionWhenFutureCompletesExceptionally() {
//    when(kafkaTemplate.send(topic, message)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Timeout")));
//
//    assertThrows(InfrastructureException.class, () -> publisher.publish(topic, message));
//  }
}
