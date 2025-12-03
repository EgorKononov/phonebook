package ru.ekononov.phonebook.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.ContactRepository;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class ContactRepositoryTest extends IntegrationTestBase {
    private final ContactRepository contactRepository;

    private final String egorFirstName = "Egor";
    private final String egorPhoneNumber = "89231234567";

    private final Contact egorContact = Contact.builder()
            .firstName(egorFirstName)
            .phoneNumber(egorPhoneNumber)
            .build();

    @Test
    void whenFindAll_thenReturnAllContact() {
        List<Contact> allContacts = contactRepository.findAll();

        assertNotNull(allContacts);
        assertEquals(5, allContacts.size());

        Optional<Contact> ivanContactOptional = allContacts.stream()
                .filter(contact -> contact.getId() == 1)
                .findFirst();

        assertTrue(ivanContactOptional.isPresent());
        Contact ivanContact = ivanContactOptional.get();
        assertEquals("Ivan", ivanContact.getFirstName());
        assertEquals("89131234567", ivanContact.getPhoneNumber());
        assertEquals(1, ivanContact.getCompany().getId());

        Optional<Contact> petrContactOptional = allContacts.stream()
                .filter(contact -> contact.getId() == 2)
                .findFirst();

        assertTrue(petrContactOptional.isPresent());
        Contact petrContact = petrContactOptional.get();
        assertEquals("Petr", petrContact.getFirstName());
        assertEquals("89171234568", petrContact.getPhoneNumber());
        assertEquals(2, petrContact.getCompany().getId());
    }

    @Test
    void whenFindByIdExistingContact_thenReturnContact() {
        Optional<Contact> foundContactOptional = contactRepository.findById(3L);

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();
        assertEquals("Sveta", foundContact.getFirstName());
        assertEquals("89141234569", foundContact.getPhoneNumber());
        assertEquals(2, foundContact.getCompany().getId());
    }

    @Test
    void whenFindByIdNotExistingContact_thenReturnEmptyOptional() {
        long notExistingId = 355235235L;
        Optional<Contact> foundContactOptional = contactRepository.findById(notExistingId);

        assertTrue(foundContactOptional.isEmpty());
    }

    @Test
    void whenSaveContact_thenFindSavedContact() {
        Contact savedContact = contactRepository.save(egorContact);
        Optional<Contact> foundContactOptional = contactRepository.findById(savedContact.getId());

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();
        assertEquals(egorFirstName, foundContact.getFirstName());
        assertEquals(egorPhoneNumber, foundContact.getPhoneNumber());
        assertEquals(savedContact, foundContact);
    }

    @Test
    void whenDeleteExistingContact_thenDeletedContactNotFound() {
        Optional<Contact> foundContactOptional = contactRepository.findById(4L);

        assertTrue(foundContactOptional.isPresent());
        Contact foundContact = foundContactOptional.get();

        contactRepository.delete(foundContact);
        contactRepository.flush();

        Optional<Contact> deletedContactOptional = contactRepository.findById(4L);
        assertTrue(deletedContactOptional.isEmpty());
    }
}