package com.example.bidder_service.factory;

import com.example.bidder_service.domain.Bid;

public class BidFactory {

  public static Bid validBid() {
    Bid bid = new Bid();
    bid.setAmount(100);
    bid.setUserId("userId-123");
    bid.setAuctionId("auctionId-123");
    return bid;
  }

  public static Bid invalidBid() {
    Bid bid = new Bid();
    bid.setAmount(50);
    return bid;
  }
}
