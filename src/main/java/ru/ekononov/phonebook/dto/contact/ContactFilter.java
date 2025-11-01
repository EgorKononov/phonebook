package ru.ekononov.phonebook.dto.contact;

import java.time.LocalDate;

public record ContactFilter(String firstName,
                            String lastName,
                            String phoneNumber,
                            LocalDate birthDate,
                            String email) {
}
