package com.example.hospital.exception;

public class BadRequestException extends RuntimeException{

    private final String statusCode;
    private final String technicalDetail;

    public BadRequestException(String statusCode, String message, String technicalDetail) {
        super( message );
        this.statusCode = statusCode;
        this.technicalDetail = technicalDetail;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getTechnicalDetail() {
        return technicalDetail;
    }

    @Override
    public String toString() {
        return "BadRequestException{" +
                "statusCode='" + statusCode + '\'' +
                ", technicalDetail='" + technicalDetail + '\'' +
                '}';
    }
}
