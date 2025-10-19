package com.example.hospital.exception;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    // Exception - obligatorio
    // RuntimeException - No chequeada
    private final String status;
    private final String detail;
    private final String code;

    public BusinessException(String status, String detail, String code) {
        this.status = status;
        this.detail = detail;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

}
