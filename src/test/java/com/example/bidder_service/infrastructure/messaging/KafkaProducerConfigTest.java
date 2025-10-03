package com.example.bidder_service.infrastructure.messaging;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = KafkaProducerConfig.class)
@ActiveProfiles("test")
public class KafkaProducerConfigTest {
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Test
  void shouldLoadKafkaTemplateWithExpectedConfigs() {
    assertNotNull(kafkaTemplate);

    ProducerFactory<String, String> factory = kafkaTemplate.getProducerFactory();
    Map<String, Object> configs =
        ((DefaultKafkaProducerFactory<String, String>) factory).getConfigurationProperties();

    assertEquals("localhost:9092", configs.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    assertSame(StringSerializer.class, configs.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    assertSame(StringSerializer.class, configs.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
  }
}
