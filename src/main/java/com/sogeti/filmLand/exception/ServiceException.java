package com.sogeti.filmLand.exception;

/**
 * This class is the custom exception class
 * 
 *
 *
 */
public class ServiceException extends Exception {

  /**
   * 
   */
  private static final long serialVersionUID = -596109118250952380L;

  /**
   * Method with the message to throw the exception.
   * 
   * @param message the error message to display.
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Method with the throwable to throw the exception.
   * 
   * @param cause the cause of an exception.
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Method with the message and the throwable to throw the exception.
   * 
   * @param message the error message to display.
   * @param cause the cause of an exception.
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
