package com.example.bidder_service.domain.exception;

public class BidProcessingException extends Exception {
  private final ErrorCode errorCode = ErrorCode.BID_PROCESSING_EXCEPTION;

  public BidProcessingException(String message, Throwable cause) {
    super(String.format("%s: %s", ErrorCode.BID_PROCESSING_EXCEPTION, message), cause);
  }

  public BidProcessingException(String message) {
    super(String.format("%s: %s", ErrorCode.BID_PROCESSING_EXCEPTION, message));
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
