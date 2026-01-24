package com.rems.exception;

public class TimeSlotOccupiedException extends RuntimeException {
    public TimeSlotOccupiedException(String message) {
        super(message);
    }
}
