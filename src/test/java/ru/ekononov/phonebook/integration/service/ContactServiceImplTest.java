package ru.ekononov.phonebook.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.common.contact.ContactTestConstants;
import ru.ekononov.phonebook.common.contact.ContactTestFactory;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.integration.IntegrationTestBase;
import ru.ekononov.phonebook.service.contact.ContactService;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;
import static ru.ekononov.phonebook.common.contact.ContactTestConstants.*;

@RequiredArgsConstructor
public class ContactServiceImplTest extends IntegrationTestBase {
    private final ContactService contactService;

    @Test
    void whenFindByIdExistingContact_thenReturnContact() {
        ContactReadDto foundContact = contactService.findById(Sveta.ID);

        assertNotNull(foundContact);
        assertEquals(Sveta.FIRST_NAME, foundContact.getFirstName());
        assertEquals(Sveta.LAST_NAME, foundContact.getLastName());
        assertEquals(Sveta.PHONE_NUMBER, foundContact.getPhoneNumber());
        assertEquals(Sveta.BIRTH_DATE, foundContact.getBirthDate());
        assertEquals(Sveta.EMAIL, foundContact.getEmail());
        assertEquals(Amazon.ID, foundContact.getCompany().id());
        assertEquals(Amazon.NAME, foundContact.getCompany().name());
    }

    @Test
    void whenFindByIdNotExistingContact_thenThrowsException() {
        assertThrows(ResponseStatusException.class, () -> contactService.findById(ContactTestConstants.NOT_EXISTING_ID));
    }

    @Test
    void whenSaveContact_thenFindSavedContact() {
        ContactReadDto createdContact = contactService.create(ContactTestFactory.egorContactCreateUpdateDto());
        ContactReadDto foundContact = contactService.findById(createdContact.getId());

        assertNotNull(foundContact);
        assertEquals(Egor.FIRST_NAME, foundContact.getFirstName());
        assertEquals(Egor.LAST_NAME, foundContact.getLastName());
        assertEquals(Egor.PHONE_NUMBER, foundContact.getPhoneNumber());
        assertEquals(Egor.BIRTH_DATE, foundContact.getBirthDate());
        assertEquals(Egor.EMAIL, foundContact.getEmail());
        assertEquals(Google.ID, foundContact.getCompany().id());
        assertEquals(Google.NAME, foundContact.getCompany().name());
        assertEquals(createdContact, foundContact);
    }

    @Test
    void whenDeleteExistingContact_thenReturnTrueAndDeletedContactNotFound() {
        assertTrue(contactService.delete(Petr.ID));
        assertThrows(ResponseStatusException.class, () -> contactService.findById(Petr.ID));
    }
}
