package ru.ekononov.phonebook.mapper.company;

import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.mapper.Mapper;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;

@Component
public class CompanyReadDtoMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto map(Company source) {
        return new CompanyReadDto(
                source.getId(),
                source.getName()
        );
    }
}
