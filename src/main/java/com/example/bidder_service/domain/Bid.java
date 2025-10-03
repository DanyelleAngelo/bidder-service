package com.example.bidder_service.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Bid {
  @NotNull(message = "amount is required")
  @Min(value = 0, message = "Amount must be greater than zero")
  private double amount;

  @NotBlank(message = "userId is required")
  private String userId;

  @NotBlank(message = "auctionId is required")
  private String auctionId;

  public Bid() {}

  public String getUserId() {
    return userId;
  }

  public String getAuctionId() {
    return auctionId;
  }

  public void setAuctionId(String auctionId) {
    this.auctionId = auctionId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
