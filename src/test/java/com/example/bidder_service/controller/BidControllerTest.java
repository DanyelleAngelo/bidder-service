package com.example.bidder_service.controller;

import com.example.bidder_service.factory.BidFactory;
import com.example.bidder_service.model.Bid;
import com.example.bidder_service.service.BidService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BidControllerTest {
  @Mock
  private BidService serviceMock;

  @InjectMocks
  private BidController controller;

  private MockMvc mvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  void setup() {
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  @DisplayName("return status code 400 when required params are missing")
  void shouldReturnAnErrorIfTheRequiredParamsIsNotReceived() throws Exception {
    Bid bid = BidFactory.invalidBid();

    mvc
        .perform(
            post("/bids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(bid))
        )
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("return status code 202 when receives a valid request")
  void shouldReturnAcceptedIfReceivesAValidRequest() throws Exception {
    Bid bid = BidFactory.validBid();

    mvc
        .perform(
            post("/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bid))
        )
        .andExpect(status().isAccepted());
  }

  @Test
  @DisplayName("returns internal server error when an error occurs in the service")
  void shouldReturnInternalServerErrorWhenServiceFails() throws Exception {
    Bid bid = BidFactory.validBid();
    doThrow(new RuntimeException("service failed"))
        .when(serviceMock)
        .publish(any(Bid.class));

    mvc.perform(post("/bids")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(bid)))
        .andExpect(status().isInternalServerError());
  }
}
