package ru.ekononov.phonebook.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ekononov.phonebook.database.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}