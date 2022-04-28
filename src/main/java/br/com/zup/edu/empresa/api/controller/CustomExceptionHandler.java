package br.com.zup.edu.empresa.api.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> erros = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        String constraintName = ex.getConstraintName();

        String message = "Erro de violação de restrição do banco de dados";

        if (constraintName.equals("UK_DEPARTAMENTO_SIGLA")) {
            message = "Já existe no sistema um departamento cadastrado com a sigla informada";
        }

        if (constraintName.equals("UK_CONTATO_TELEFONE_DEPARTAMENTO")) {
            message = "Número de telefone já cadastrado para esta empresa";
        }

        Map<String, Object> body = Map.of(
                "status", 422,
                "error", "UNPROCESSABLE ENTITY",
                "path", request.getDescription(false).replace("uri=", ""),
                "timestamp", LocalDateTime.now(),
                "message", message
        );

        return ResponseEntity.unprocessableEntity().body(body);
    }
}
