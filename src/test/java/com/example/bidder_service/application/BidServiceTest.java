package com.example.bidder_service.application;

import com.example.bidder_service.domain.Bid;
import com.example.bidder_service.domain.exception.BidProcessingException;
import com.example.bidder_service.domain.exception.InfrastructureException;
import com.example.bidder_service.domain.messaging.MessagePublisher;
import com.example.bidder_service.factory.BidFactory;
import com.example.bidder_service.infrastructure.messaging.KafkaProducerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BidServiceTest {
  BidService service;
  @Mock
  MessagePublisher publisher;
  private ObjectMapper mapper;

  @BeforeEach
  void setUp() {
    mapper =  new ObjectMapper();
    service = new BidService(publisher, "submitted-bids", mapper);
  }

  @Test
  void shouldInvokePublisherWithMessageAndDestination() throws Exception {
    Bid bid = BidFactory.validBid();
    String expectedMessage = mapper.writeValueAsString(bid);

    service.publish(bid);

    verify(publisher, times(1)).publish("submitted-bids", expectedMessage);
  }

  @Test
  void shouldThrowBidProcessingExceptionWhenSerializationFails() throws Exception  {
    mapper = mock(ObjectMapper.class);
    Bid bid = BidFactory.validBid();
    when(mapper.writeValueAsString(bid)).thenThrow(new JsonProcessingException("fail")  {});

    BidService service = new BidService(publisher, "submitted-bids", mapper);

    assertThrows(BidProcessingException.class, () -> {
      service.publish(bid);
    });
  }
}
