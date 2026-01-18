package ru.ekononov.phonebook.mapper.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.mapper.Mapper;
import ru.ekononov.phonebook.mapper.company.CompanyReadDtoMapper;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactReadDtoMapper implements Mapper<Contact, ContactReadDto> {
    private final CompanyReadDtoMapper companyReadDtoMapper;

    @Override
    public ContactReadDto map(Contact source) {
        return new ContactReadDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber(),
                source.getBirthDate(),
                source.getEmail(),
                Optional.ofNullable(source.getCompany())
                        .map(companyReadDtoMapper::map)
                        .orElse(null)
        );
    }
}
