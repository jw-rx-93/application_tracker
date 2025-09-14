package com.jobtracker.backend.exception;


public class UserExceptions {
  public static class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
      super("DUPLICATE_EMAIL", "Email already exists");
    }
  }

  public static class InvalidUserDataException extends BusinessException {
    public InvalidUserDataException(String message) {
      super("USER_DATA", message);
    }
  }
}
