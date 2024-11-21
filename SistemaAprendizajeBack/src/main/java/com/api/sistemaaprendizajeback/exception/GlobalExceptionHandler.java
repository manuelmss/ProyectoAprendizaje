package com.api.sistemaaprendizajeback.exception;


import com.api.sistemaaprendizajeback.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> manejarRecursoNoEncontradoException(NoHandlerFoundException ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                false,
                "Recurso no encontrado",
                null,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> manejarExcepcionGeneral(Exception ex) {
        ApiResponse<Void> response = new ApiResponse<>(
                false,
                "Error interno del servidor: " + ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> manejarArgumentoNoValido(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        ApiResponse<Map<String, String>> response = new ApiResponse<>(
                false,
                "Validación fallida",
                errores,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
