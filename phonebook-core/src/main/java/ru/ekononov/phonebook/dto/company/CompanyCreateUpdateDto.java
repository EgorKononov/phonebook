package ru.ekononov.phonebook.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
public record CompanyCreateUpdateDto(
        @NotBlank String name
) {
}
