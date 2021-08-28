package com.tabakov.exception;

/**
 * Thrown if division name doesn't match defined
 * in {@link com.tabakov.util.DivisionValidator#DIVISION_REGEX} rule
 */
public class InvalidDivisionCodeException extends Exception {
    public InvalidDivisionCodeException(String message) {
        super(message);
    }
}
