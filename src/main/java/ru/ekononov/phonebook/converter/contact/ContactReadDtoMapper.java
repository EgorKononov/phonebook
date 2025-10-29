package ru.ekononov.phonebook.converter.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.converter.Mapper;
import ru.ekononov.phonebook.converter.company.CompanyReadDtoMapper;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

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
