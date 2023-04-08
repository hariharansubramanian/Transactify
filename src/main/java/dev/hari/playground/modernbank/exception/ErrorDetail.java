package dev.hari.playground.modernbank.exception;

public class ErrorDetail {
    public int status;
    public String message;

    public ErrorDetail(int status, String message) {
        this.status = status;
        this.message = message;
    }
}