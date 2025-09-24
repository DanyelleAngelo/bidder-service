package com.example.bidder_service.controller;

import com.example.bidder_service.factory.BidFactory;
import com.example.bidder_service.model.Bid;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BidController.class)
public class BidControllerTest {
  @Autowired
  private MockMvc mvc;
  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  @DisplayName("return status code 400 when required params are missing")
  void shouldReturnAnErrorIfTheRequiredParamsIsNotReceived() throws Exception{
    Bid bid = BidFactory.invalidBid();
    mvc
        .perform(
            post("/bids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bid))
        )
        .andExpect(status().isBadRequest());
  }
}
