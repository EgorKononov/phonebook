package ru.ekononov.phonebook.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ekononov.phonebook.dto.ErrorResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .timestamp(Instant.now())
                        .status(status.value())
                        .error("Validation Error")
                        .errors(buildErrors(e.getBindingResult()))
                        .build(),
                headers,
                status);
    }

    private static List<Map<String, String>> buildErrors(BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return bindingResult.getFieldErrors()
                    .stream()
                    .map(ex -> Map.of(
                            "field", ex.getField(),
                            "message", ex.getDefaultMessage()
                    ))
                    .toList();
        } else {
            return bindingResult.getAllErrors()
                    .stream()
                    .map(ex -> Map.of(
                            "message", ex.getDefaultMessage()
                    ))
                    .toList();
        }
    }
}
