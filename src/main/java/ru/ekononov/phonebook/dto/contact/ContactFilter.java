package ru.ekononov.phonebook.dto.contact;

import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@FieldNameConstants
public record ContactFilter(
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        String email
) {
}
