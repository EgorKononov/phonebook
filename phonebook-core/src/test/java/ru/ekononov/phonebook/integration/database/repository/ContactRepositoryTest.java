package ru.ekononov.phonebook.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.ekononov.phonebook.common.contact.ContactTestConstants;
import ru.ekononov.phonebook.common.contact.ContactTestFactory;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.database.repository.ContactRepository;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;
import static ru.ekononov.phonebook.common.contact.ContactTestConstants.*;

@RequiredArgsConstructor
class ContactRepositoryTest extends IntegrationTestBase {
    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    @Test
    void whenFindAll_thenReturnAllContact() {
        List<Contact> allContacts = contactRepository.findAll();

        assertNotNull(allContacts);
        assertEquals(5, allContacts.size());

        Optional<Contact> ivanContactOptional = allContacts.stream()
                .filter(contact -> contact.getId() == Ivan.ID)
                .findFirst();

        assertTrue(ivanContactOptional.isPresent());
        Contact ivanContact = ivanContactOptional.get();
        assertEquals(Ivan.FIRST_NAME, ivanContact.getFirstName());
        assertEquals(Ivan.LAST_NAME, ivanContact.getLastName());
        assertEquals(Ivan.PHONE_NUMBER, ivanContact.getPhoneNumber());
        assertEquals(Ivan.BIRTH_DATE, ivanContact.getBirthDate());
        assertEquals(Ivan.EMAIL, ivanContact.getEmail());
        assertEquals(Google.ID, ivanContact.getCompany().getId());
        assertEquals(Google.NAME, ivanContact.getCompany().getName());

        Optional<Contact> petrContactOptional = allContacts.stream()
                .filter(contact -> contact.getId() == Petr.ID)
                .findFirst();

        assertTrue(petrContactOptional.isPresent());
        Contact petrContact = petrContactOptional.get();
        assertEquals(Petr.FIRST_NAME, petrContact.getFirstName());
        assertEquals(Petr.LAST_NAME, petrContact.getLastName());
        assertEquals(Petr.PHONE_NUMBER, petrContact.getPhoneNumber());
        assertEquals(Petr.BIRTH_DATE, petrContact.getBirthDate());
        assertEquals(Petr.EMAIL, petrContact.getEmail());
        assertEquals(Amazon.ID, petrContact.getCompany().getId());
        assertEquals(Amazon.NAME, petrContact.getCompany().getName());
    }

    @Test
    void whenFindByIdExistingContact_thenReturnContact() {
        Optional<Contact> foundContactOptional = contactRepository.findById(Sveta.ID);

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();
        assertEquals(Sveta.FIRST_NAME, foundContact.getFirstName());
        assertEquals(Sveta.LAST_NAME, foundContact.getLastName());
        assertEquals(Sveta.PHONE_NUMBER, foundContact.getPhoneNumber());
        assertEquals(Sveta.BIRTH_DATE, foundContact.getBirthDate());
        assertEquals(Sveta.EMAIL, foundContact.getEmail());
        assertEquals(Amazon.ID, foundContact.getCompany().getId());
        assertEquals(Amazon.NAME, foundContact.getCompany().getName());
    }

    @Test
    void whenFindByIdNotExistingContact_thenReturnEmptyOptional() {
        Optional<Contact> foundContactOptional = contactRepository.findById(ContactTestConstants.NOT_EXISTING_ID);

        assertTrue(foundContactOptional.isEmpty());
    }

    @Test
    void whenSaveContact_thenReturnSavedContact() {
        Contact savedContact = contactRepository.save(ContactTestFactory.egorContact(companyRepository));
        Optional<Contact> foundContactOptional = contactRepository.findById(savedContact.getId());

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();
        assertEquals(Egor.FIRST_NAME, foundContact.getFirstName());
        assertEquals(Egor.LAST_NAME, foundContact.getLastName());
        assertEquals(Egor.PHONE_NUMBER, foundContact.getPhoneNumber());
        assertEquals(Egor.BIRTH_DATE, foundContact.getBirthDate());
        assertEquals(Egor.EMAIL, foundContact.getEmail());
        assertEquals(Google.ID, foundContact.getCompany().getId());
        assertEquals(Google.NAME, foundContact.getCompany().getName());
        assertEquals(savedContact, foundContact);
    }

    @Test
    void whenDeleteExistingContact_thenDeletedContactNotFound() {
        Optional<Contact> foundContactOptional = contactRepository.findById(Sveta.ID);

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();

        contactRepository.delete(foundContact);
        contactRepository.flush();

        Optional<Contact> deletedContactOptional = contactRepository.findById(Sveta.ID);
        assertTrue(deletedContactOptional.isEmpty());
    }
}