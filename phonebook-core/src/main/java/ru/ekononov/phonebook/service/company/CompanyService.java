package ru.ekononov.phonebook.service.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyFilter;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;

import java.util.Optional;

public interface CompanyService {
    CompanyReadDto findById(long id);

    Page<CompanyReadDto> find(CompanyFilter filter, Pageable pageable);

    CompanyReadDto create(CompanyCreateUpdateDto company);

    Optional<CompanyReadDto> update(Long id, CompanyCreateUpdateDto company);

    boolean delete(Long id);
}
