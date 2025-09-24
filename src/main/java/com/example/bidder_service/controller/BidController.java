package com.example.bidder_service.controller;

import com.example.bidder_service.model.Bid;
import com.example.bidder_service.service.BidService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bids")
public class BidController {
  @Autowired
  BidService service;

  @PostMapping
  public ResponseEntity<Void> submitBid(@RequestBody @Valid Bid bid) {
    try {
      service.publish(bid);
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    } catch (Exception error) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
