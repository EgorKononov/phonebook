package ru.ekononov.phonebook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.converter.contact.ContactMapper;
import ru.ekononov.phonebook.converter.contact.ContactReadDtoMapper;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.querydsl.QPredicates;
import ru.ekononov.phonebook.database.repository.ContactRepository;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactFilter;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

import java.util.Optional;

import static ru.ekononov.phonebook.database.entity.QContact.contact;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ContactReadDtoMapper contactReadDtoMapper;

    @Override
    public ContactReadDto findById(long id) {
        return contactRepository.findById(id)
                .map(contactReadDtoMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<ContactReadDto> find(ContactFilter filter, Pageable pageable) {
        return contactRepository.findAll(
                        QPredicates.builder()
                                .add(filter.firstName(), contact.firstName::containsIgnoreCase)
                                .add(filter.lastName(), contact.lastName::containsIgnoreCase)
                                .add(filter.email(), contact.email::containsIgnoreCase)
                                .add(filter.phoneNumber(), contact.phoneNumber::containsIgnoreCase)
                                .add(filter.birthDate(), contact.birthDate::before)
                                .build(),
                        pageable)
                .map(contactReadDtoMapper::map);
    }

    @Override
    @Transactional
    public ContactReadDto create(ContactCreateUpdateDto contact) {
        Contact savedContact = contactRepository.save(contactMapper.map(contact));
        log.info("Contact added {}", savedContact);

        return contactReadDtoMapper.map(savedContact);
    }

    @Override
    @Transactional
    public Optional<ContactReadDto> update(Long id, ContactCreateUpdateDto contact) {
        return contactRepository.findById(id)
                .map(contactToUpdate -> contactMapper.map(contact, contactToUpdate))
                .map(contactRepository::saveAndFlush)
                .map(contactReadDtoMapper::map);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return contactRepository.findById(id)
                .map(contact -> {
                    contactRepository.delete(contact);
                    contactRepository.flush();

                    return true;
                })
                .orElse(false);
    }
}
