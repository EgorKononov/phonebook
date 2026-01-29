package ru.ekononov.phonebook.mapper.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.mapper.Mapper;

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
        contact.setFirstName(source.getFirstName());
        contact.setLastName(source.getLastName());
        contact.setPhoneNumber(source.getPhoneNumber());
        contact.setBirthDate(source.getBirthDate());
        contact.setEmail(source.getEmail());

        contact.setCompany(Optional.ofNullable(source.getCompanyId())
                .flatMap(companyRepository::findById)
                .orElse(null));
    }
}
