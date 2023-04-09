package dev.hari.playground.transactify.dto;

public class ErrorDetail {
    public int status;
    public String message;

    public ErrorDetail(int status, String message) {
        this.status = status;
        this.message = message;
    }
}