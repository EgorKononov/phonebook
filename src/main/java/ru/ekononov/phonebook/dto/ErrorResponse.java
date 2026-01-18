package ru.ekononov.phonebook.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class ErrorResponse {
    Instant timestamp;
    int status;
    String error;
    List<Map<String, String>> errors;
}
