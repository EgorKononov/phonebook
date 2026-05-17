package ru.ekononov.phonebook.common.contact;

import lombok.experimental.UtilityClass;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;
import static ru.ekononov.phonebook.common.contact.ContactTestConstants.*;

@UtilityClass
public class ContactTestFactory {
    public Contact egorContact(CompanyRepository companyRepository) {
        return Contact.builder()
                .firstName(Egor.FIRST_NAME)
                .lastName(Egor.LAST_NAME)
                .phoneNumber(Egor.PHONE_NUMBER)
                .birthDate(Egor.BIRTH_DATE)
                .email(Egor.EMAIL)
                .company(companyRepository.findById(Google.ID).orElseThrow())
                .build();
    }

    public Contact egorContact() {
        return Contact.builder()
                .firstName(Egor.FIRST_NAME)
                .lastName(Egor.LAST_NAME)
                .phoneNumber(Egor.PHONE_NUMBER)
                .birthDate(Egor.BIRTH_DATE)
                .email(Egor.EMAIL)
                .company(new Company(
                        Google.ID,
                        Google.NAME))
                .build();
    }

    public ContactReadDto egorContactReadDto() {
        return new ContactReadDto(
                Egor.ID,
                Egor.FIRST_NAME,
                Egor.LAST_NAME,
                Egor.PHONE_NUMBER,
                Egor.BIRTH_DATE,
                Egor.EMAIL,
                new CompanyReadDto(
                        Google.ID,
                        Google.NAME)
        );
    }

    public ContactCreateUpdateDto egorContactCreateUpdateDto() {
        return new ContactCreateUpdateDto(
                Egor.FIRST_NAME,
                Egor.LAST_NAME,
                Egor.PHONE_NUMBER,
                Egor.BIRTH_DATE,
                Egor.EMAIL,
                Google.ID);
    }

    public ContactCreateUpdateDto updatedIvanContactCreateUpdateDto() {
        return new ContactCreateUpdateDto(
                Ivan.FIRST_NAME,
                Ivan.LAST_NAME,
                Ivan.PHONE_NUMBER,
                Ivan.BIRTH_DATE,
                Ivan.UPDATED_EMAIL,
                Google.ID);
    }

    public ContactCreateUpdateDto invalidEmailContactCreateUpdateDto() {
        return ContactCreateUpdateDto.builder()
                .firstName(DefaultContact.FIRST_NAME)
                .phoneNumber(DefaultContact.PHONE_NUMBER)
                .email(INVALID_EMAIL)
                .build();
    }

    public ContactCreateUpdateDto invalidContactNameContactCreateUpdateDto() {
        return ContactCreateUpdateDto.builder()
                .phoneNumber(DefaultContact.PHONE_NUMBER)
                .email(DefaultContact.EMAIL)
                .build();
    }

    public ContactCreateUpdateDto emptyPhoneNumberContactCreateUpdateDto() {
        return ContactCreateUpdateDto.builder()
                .firstName(DefaultContact.FIRST_NAME)
                .email(DefaultContact.EMAIL)
                .build();
    }
}
