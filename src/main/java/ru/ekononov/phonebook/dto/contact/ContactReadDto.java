package ru.ekononov.phonebook.dto.contact;

import ru.ekononov.phonebook.dto.company.CompanyReadDto;

import java.time.LocalDate;

public record ContactReadDto(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        String email,
        CompanyReadDto company) {
}
