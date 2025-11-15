package ru.ekononov.phonebook.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

public record CompanyCreateUpdateDto(
        @NotBlank String name
) {
}
