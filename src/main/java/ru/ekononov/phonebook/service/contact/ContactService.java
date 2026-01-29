package ru.ekononov.phonebook.service.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactFilter;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

import java.util.Optional;

public interface ContactService {
    ContactReadDto findById(long id);

    Page<ContactReadDto> find(ContactFilter filter, Pageable pageable);

    ContactReadDto create(ContactCreateUpdateDto contact);

    Optional<ContactReadDto> update(Long id, ContactCreateUpdateDto contact);

    boolean delete(Long id);
}
