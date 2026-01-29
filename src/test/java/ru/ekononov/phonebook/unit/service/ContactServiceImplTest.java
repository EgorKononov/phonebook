package ru.ekononov.phonebook.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.common.company.CompanyTestConstants;
import ru.ekononov.phonebook.common.contact.ContactTestFactory;
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
import static ru.ekononov.phonebook.common.contact.ContactTestConstants.*;

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

    @Test
    void whenFindByIdExistingContact_thenReturnContact() {
        Contact egorContact = ContactTestFactory.egorContact();

        doReturn(Optional.of(egorContact))
                .when(contactRepository)
                .findById(Egor.ID);

        doReturn(ContactTestFactory.egorContactReadDto())
                .when(contactReadDtoMapper)
                .map(egorContact);

        ContactReadDto foundContact = contactService.findById(Egor.ID);

        assertNotNull(foundContact);
        assertEquals(Egor.FIRST_NAME, foundContact.getFirstName());
        assertEquals(Egor.LAST_NAME, foundContact.getLastName());
        assertEquals(Egor.PHONE_NUMBER, foundContact.getPhoneNumber());
        assertEquals(Egor.BIRTH_DATE, foundContact.getBirthDate());
        assertEquals(Egor.EMAIL, foundContact.getEmail());
        assertEquals(CompanyTestConstants.Google.ID, foundContact.getCompany().id());
        assertEquals(CompanyTestConstants.Google.NAME, foundContact.getCompany().name());

        verify(contactRepository, times(1)).findById(Egor.ID);
        verify(contactReadDtoMapper, times(1)).map(egorContact);
        verifyNoMoreInteractions(contactRepository, contactMapper, contactReadDtoMapper);
    }


    @Test
    void whenFindByIdNotExistingContact_thenThrowsException() {
        doReturn(Optional.empty())
                .when(contactRepository)
                .findById(NOT_EXISTING_ID);

        assertThrows(ResponseStatusException.class, () -> contactService.findById(NOT_EXISTING_ID));

        verify(contactRepository, times(1)).findById(NOT_EXISTING_ID);
        verifyNoMoreInteractions(contactRepository, contactMapper, contactReadDtoMapper);
    }

    @Test
    void whenSaveContact_thenReturnSavedContact() {
        Contact egorContact = ContactTestFactory.egorContact();

        doReturn(egorContact)
                .when(contactRepository)
                .save(egorContact);

        doReturn(ContactTestFactory.egorContactReadDto())
                .when(contactReadDtoMapper)
                .map(egorContact);

        ContactCreateUpdateDto egorContactCreateUpdateDto = ContactTestFactory.egorContactCreateUpdateDto();

        doReturn(egorContact)
                .when(contactMapper)
                .map(egorContactCreateUpdateDto);

        ContactReadDto createdContact = contactService.create(egorContactCreateUpdateDto);

        assertNotNull(createdContact);
        assertEquals(Egor.FIRST_NAME, createdContact.getFirstName());
        assertEquals(Egor.LAST_NAME, createdContact.getLastName());
        assertEquals(Egor.PHONE_NUMBER, createdContact.getPhoneNumber());
        assertEquals(Egor.BIRTH_DATE, createdContact.getBirthDate());
        assertEquals(Egor.EMAIL, createdContact.getEmail());
        assertEquals(CompanyTestConstants.Google.ID, createdContact.getCompany().id());
        assertEquals(CompanyTestConstants.Google.NAME, createdContact.getCompany().name());

        verify(contactRepository, times(1)).save(egorContact);
        verify(contactMapper, times(1)).map(egorContactCreateUpdateDto);
        verify(contactReadDtoMapper, times(1)).map(egorContact);
        verifyNoMoreInteractions(contactRepository, contactMapper, contactReadDtoMapper);
    }
}
