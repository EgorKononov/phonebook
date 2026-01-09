package ru.ekononov.phonebook.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.ContactRepository;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.mapper.contact.ContactMapper;
import ru.ekononov.phonebook.mapper.contact.ContactReadDtoMapper;
import ru.ekononov.phonebook.service.contact.ContactServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceImplTest {
    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private ContactReadDtoMapper contactReadDtoMapper;

    @InjectMocks
    private ContactServiceImpl contactService;

    private final long egorId = 1L;
    private final String egorFirstName = "Egor";
    private final String egorPhoneNumber = "89131234567";

    private Contact egorContact;
    private ContactReadDto egorContactReadDto;
    private ContactCreateUpdateDto egorContactCreateUpdateDto;

    @BeforeEach
    void setUp() {
        egorContact = Contact.builder()
                .id(egorId)
                .firstName(egorFirstName)
                .phoneNumber(egorPhoneNumber)
                .build();

        egorContactReadDto = new ContactReadDto(
                egorId,
                egorFirstName,
                null,
                egorPhoneNumber,
                null,
                null,
                null);

        egorContactCreateUpdateDto = new ContactCreateUpdateDto(
                egorFirstName,
                null,
                egorPhoneNumber,
                null,
                null,
                null
        );
    }

    @Test
    void whenFindByIdExistingContact_thenReturnContact() {
        doReturn(Optional.of(egorContact))
                .when(contactRepository)
                .findById(egorId);

        doReturn(egorContactReadDto)
                .when(contactReadDtoMapper)
                .map(egorContact);

        ContactReadDto foundContact = contactService.findById(egorId);

        assertNotNull(foundContact);
        assertEquals(egorFirstName, foundContact.firstName());
        assertEquals(egorPhoneNumber, foundContact.phoneNumber());

        verify(contactRepository, times(1)).findById(egorId);
        verify(contactReadDtoMapper, times(1)).map(egorContact);
        verifyNoMoreInteractions(contactRepository,contactMapper,contactReadDtoMapper);
    }


    @Test
    void whenFindByIdNotExistingContact_thenThrowsException() {
        long notExistingId = 355235235L;

        doReturn(Optional.empty())
                .when(contactRepository)
                .findById(notExistingId);

        assertThrows(ResponseStatusException.class, () -> contactService.findById(notExistingId));

        verify(contactRepository, times(1)).findById(notExistingId);
        verifyNoMoreInteractions(contactRepository,contactMapper,contactReadDtoMapper);
    }

    @Test
    void whenSaveContact_thenReturnSavedContact() {
        doReturn(egorContact)
                .when(contactRepository)
                .save(egorContact);

        doReturn(egorContactReadDto)
                .when(contactReadDtoMapper)
                .map(egorContact);

        doReturn(egorContact)
                .when(contactMapper)
                .map(egorContactCreateUpdateDto);

        ContactReadDto createdContact = contactService.create(egorContactCreateUpdateDto);

        assertNotNull(createdContact);
        assertEquals(egorId, createdContact.id());
        assertEquals(egorFirstName, createdContact.firstName());
        assertEquals(egorPhoneNumber, createdContact.phoneNumber());

        verify(contactRepository, times(1)).save(egorContact);
        verify(contactMapper, times(1)).map(egorContactCreateUpdateDto);
        verify(contactReadDtoMapper, times(1)).map(egorContact);
        verifyNoMoreInteractions(contactRepository,contactMapper,contactReadDtoMapper);
    }
}
