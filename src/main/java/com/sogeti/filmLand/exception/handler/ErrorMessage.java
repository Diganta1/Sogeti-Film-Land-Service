package com.sogeti.filmLand.exception.handler;

import org.springframework.http.HttpStatus.Series;

import com.google.gson.annotations.SerializedName;

/**
 * 
 *
 */
public class ErrorMessage {

  @SerializedName("code")
  private Series code;
  
  @SerializedName("status")
  private String status;
  
  @SerializedName("message")
  private String message;

  /**
   * This constructs the error message with properties namely
   * code, status, and message for the error occurred.
   * @param clientError the code for the error occurred 
   * @param status the status of the error occurred
   * @param message the cause of error occurred 
   */
  public ErrorMessage(Series clientError, String status, String message) {
    this.code = clientError;
    this.status = status;
    this.message = message;
  }

  public Series getCode() {
    return code;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

}
