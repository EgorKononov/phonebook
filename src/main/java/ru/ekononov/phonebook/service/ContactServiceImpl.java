package ru.ekononov.phonebook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekononov.phonebook.converter.contact.ContactMapper;
import ru.ekononov.phonebook.converter.contact.ContactReadDtoMapper;
import ru.ekononov.phonebook.database.entity.Contact;
import ru.ekononov.phonebook.database.repository.ContactRepository;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final ContactReadDtoMapper contactReadDtoMapper;

    @Override
    public List<ContactReadDto> find(String term) {
        if (term == null || term.isBlank()) {
            return contactReadDtoMapper.map(contactRepository.findAll());
        }

        String termTrim = EscapeCharacter.DEFAULT.escape(term.trim());
        log.debug("Searching contacts for term [{}]", termTrim);

        return contactReadDtoMapper.map(contactRepository.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContainsIgnoreCaseOrEmailContainsIgnoreCase(
                termTrim,
                termTrim,
                termTrim,
                termTrim));
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
