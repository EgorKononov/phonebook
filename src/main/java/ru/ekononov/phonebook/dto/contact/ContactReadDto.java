package ru.ekononov.phonebook.dto.contact;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class ContactReadDto {
    Long id;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthDate;
    String email;
    CompanyReadDto company;
}
