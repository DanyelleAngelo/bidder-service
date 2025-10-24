package com.example.bidder_service.application;

import com.example.bidder_service.domain.Bid;
import com.example.bidder_service.domain.exception.BidProcessingException;
import com.example.bidder_service.domain.exception.InfrastructureException;
import com.example.bidder_service.domain.messaging.MessagePublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BidService {
  MessagePublisher publisher;
  String target;
  ObjectMapper mapper;
  private static final Logger logger = LoggerFactory.getLogger(BidService.class);


  @Autowired
  public BidService(MessagePublisher publisher, @Value("${messaging.targets.bid}") String target, ObjectMapper mapper) {
    this.publisher = publisher;
    this.target = target;
    this.mapper = mapper;
  }

  public void publish(Bid bid) throws BidProcessingException, InfrastructureException {
    String message = serialize(bid);

    publisher.publish(target, message);
  }

  private String serialize(Bid bid) throws BidProcessingException {
    try {
      return mapper.writeValueAsString(bid);
    } catch (JsonProcessingException e) {
      logger.error("An error occurred when trying to serialize message.");
      throw new BidProcessingException("Failed to serialize bid", e);
    }
  }
}
