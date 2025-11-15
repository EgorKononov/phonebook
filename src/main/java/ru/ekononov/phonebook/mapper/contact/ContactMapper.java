package ru.ekononov.phonebook.mapper.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.mapper.Mapper;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContactMapper implements Mapper<ContactCreateUpdateDto, Contact> {
    private final CompanyRepository companyRepository;

    @Override
    public Contact map(ContactCreateUpdateDto source) {
        Contact contact = new Contact();
        copy(source, contact);

        return contact;
    }

    @Override
    public Contact map(ContactCreateUpdateDto fromObject, Contact toObject) {
        copy(fromObject, toObject);

        return toObject;
    }

    private void copy(ContactCreateUpdateDto source, Contact contact) {
        contact.setFirstName(source.firstName());
        contact.setLastName(source.lastName());
        contact.setPhoneNumber(source.phoneNumber());
        contact.setBirthDate(source.birthDate());
        contact.setEmail(source.email());

        contact.setCompany(Optional.ofNullable(source.companyId())
                .flatMap(companyRepository::findById)
                .orElse(null));
    }
}
