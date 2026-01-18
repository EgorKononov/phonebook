package ru.ekononov.phonebook.dto.contact;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import ru.ekononov.phonebook.validation.ContactName;
import ru.ekononov.phonebook.validation.group.UpdateAction;

import java.time.LocalDate;

@Value
@AllArgsConstructor
@Builder
@FieldNameConstants
@ContactName
public class ContactCreateUpdateDto {
    @Size(max = 64)
    String firstName;

    @Size(max = 64)
    String lastName;

    @NotBlank
    String phoneNumber;

    @NotNull(groups = UpdateAction.class)
    LocalDate birthDate;

    @Email
    @NotBlank(groups = UpdateAction.class)
    String email;

    Long companyId;
}
