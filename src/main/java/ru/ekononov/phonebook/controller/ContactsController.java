package ru.ekononov.phonebook.controller;

import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactFilter;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.service.contact.ContactService;
import ru.ekononov.phonebook.validation.group.CreateAction;
import ru.ekononov.phonebook.validation.group.UpdateAction;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Slf4j
public class ContactsController {
    private final ContactService contactService;

    @GetMapping("/{id}")
    public ContactReadDto findById(@PathVariable long id) {
        return contactService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ContactReadDto>> find(ContactFilter filter, Pageable pageable) {
        return new ResponseEntity<>(
                contactService.find(filter, pageable),
                HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactReadDto create(@RequestBody
                                 @Validated({Default.class, CreateAction.class})
                                 ContactCreateUpdateDto contact) {
        return contactService.create(contact);
    }

    @PutMapping("/{id}")
    public ContactReadDto update(@PathVariable
                                 Long id,

                                 @RequestBody
                                 @Validated({Default.class, UpdateAction.class})
                                 ContactCreateUpdateDto contact) {
        return contactService.update(id, contact)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!contactService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
