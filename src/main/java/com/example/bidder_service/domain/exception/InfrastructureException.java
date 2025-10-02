package com.example.bidder_service.domain.exception;

public class InfrastructureException extends Exception {
  private final ErrorCode errorCode = ErrorCode.INFRASTRUCTURE_EXCEPTION;

  public InfrastructureException(String message, Throwable cause) {
    super(String.format("%s: %s", ErrorCode.INFRASTRUCTURE_EXCEPTION, message), cause);
  }

  public InfrastructureException(String message) {
    super(String.format("%s: %s", ErrorCode.INFRASTRUCTURE_EXCEPTION, message));
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
