package ru.ekononov.phonebook.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ekononov.phonebook.database.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContainsIgnoreCaseOrEmailContainsIgnoreCase(
            String firstName,
            String lastName,
            String phoneNumber,
            String email);

    Optional<Contact> findByPhoneNumberIgnoreCase(String phoneNumber);

    Optional<Contact> findByEmailIgnoreCase(String email);
}