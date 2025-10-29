package ru.ekononov.phonebook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactReadDto;
import ru.ekononov.phonebook.service.ContactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Slf4j
public class ContactsController {
    private final ContactService contactService;

    @GetMapping
    public List<ContactReadDto> find(@RequestParam(required = false) String term) {
        return contactService.find(term);
    }

    @PostMapping
    public ContactReadDto create(@RequestBody ContactCreateUpdateDto contact) {
        return contactService.create(contact);
    }

    @PutMapping("/{id}")
    public Optional<ContactReadDto> update(@PathVariable Long id, @RequestBody ContactCreateUpdateDto contact) {
        return contactService.update(id, contact);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!contactService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
