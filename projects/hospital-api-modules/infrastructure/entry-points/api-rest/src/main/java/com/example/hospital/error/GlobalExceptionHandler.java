package com.example.hospital.error;

import com.example.hospital.exception.BadRequestException;
import com.example.hospital.exception.BusinessException;
import com.example.hospital.exception.MongoConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        body.put("detail", ex.getDetail());
        body.put("code", ex.getCode());
        body.put("error", "Error de negocio");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleBadRequestException(BadRequestException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode());
        body.put("detail", ex.getTechnicalDetail());
        body.put("error", "Solicitud inválida");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MongoConnectionException.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleMongoConnectionException(MongoConnectionException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", "SERVICE_UNAVAILABLE");
        body.put("detail", "No se pudo conectar a la base de datos MongoDB: " + ex.getMessage());
        body.put("error", "Error de conexión");
        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", "INTERNAL_SERVER_ERROR");
        body.put("detail", "Ocurrió un error inesperado: " + ex.getMessage());
        body.put("error", "Error interno del servidor");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

