package ru.ekononov.phonebook.service;

import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<ContactReadDto> find(String term);

    ContactReadDto create(ContactCreateUpdateDto contact);

    Optional<ContactReadDto> update(Long id, ContactCreateUpdateDto contact);

    boolean delete(Long id);
}
