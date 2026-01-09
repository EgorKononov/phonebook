package ru.ekononov.phonebook.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.integration.IntegrationTestBase;
import ru.ekononov.phonebook.service.contact.ContactService;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class ContactServiceImplTest extends IntegrationTestBase {
    private final ContactService contactService;
    private final String firstName = "Egor";
    private final String phoneNumber = "89231234567";
    private final long companyId = 1L;
    private final ContactCreateUpdateDto egorContact = new ContactCreateUpdateDto(firstName, null, phoneNumber, null, null, companyId);

    @Test
    void whenFindByIdExistingContact_thenReturnContact(){
        ContactReadDto foundContact = contactService.findById(3L);

        assertNotNull(foundContact);
        assertEquals("Sveta", foundContact.firstName());
        assertEquals("89141234569", foundContact.phoneNumber());
        assertEquals(2, foundContact.company().id());
    }

    @Test
    void whenFindByIdNotExistingContact_thenThrowsException() {
        long notExistingId = 355235235L;
        assertThrows(ResponseStatusException.class, () ->  contactService.findById(notExistingId));
    }

    @Test
    void whenSaveContact_thenFindSavedContact() {
        ContactReadDto createdContact = contactService.create(egorContact);
        ContactReadDto foundContact = contactService.findById(createdContact.id());

        assertNotNull(foundContact);
        assertEquals(firstName, foundContact.firstName());
        assertEquals(phoneNumber, foundContact.phoneNumber());
        assertEquals(companyId, foundContact.company().id());
    }

    @Test
    void whenDeleteExistingContact_thenReturnTrueAndDeletedContactNotFound() {
        long contactId = 4L;
        assertTrue(contactService.delete(contactId));
        assertThrows(ResponseStatusException.class, () ->  contactService.findById(contactId));
    }
}
