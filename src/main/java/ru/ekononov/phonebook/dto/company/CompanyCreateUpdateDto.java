package ru.ekononov.phonebook.dto.company;

import lombok.Value;

@Value
public class CompanyCreateUpdateDto {
    Long id;
    String name;
}
