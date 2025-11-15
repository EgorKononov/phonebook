package ru.ekononov.phonebook.dto.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.ekononov.phonebook.validation.ContactName;

import java.time.LocalDate;

@ContactName
public record ContactCreateUpdateDto(
        @Size(max = 64) String firstName,
        @Size(max = 64) String lastName,
        @NotBlank String phoneNumber,
        LocalDate birthDate,
        @Email String email,
        Long companyId
) {
}
