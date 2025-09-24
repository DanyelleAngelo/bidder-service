package com.example.bidder_service.controller;

import com.example.bidder_service.model.Bid;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bids")
public class BidController {
  @PostMapping
  public ResponseEntity<Void> submitBid(@RequestBody @Valid Bid bid) {
    System.out.println("teste");
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
