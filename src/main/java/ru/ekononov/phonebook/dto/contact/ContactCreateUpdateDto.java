package ru.ekononov.phonebook.dto.contact;

import lombok.Value;

import java.time.LocalDate;

@Value
public class ContactCreateUpdateDto {
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthDate;
    String email;
    Long companyId;
}
