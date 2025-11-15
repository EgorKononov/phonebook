package ru.ekononov.phonebook.controller;

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
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyFilter;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;
import ru.ekononov.phonebook.service.company.CompanyService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{id}")
    public CompanyReadDto findById(@PathVariable long id) {
        return companyService.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CompanyReadDto>> find(CompanyFilter filter, Pageable pageable) {
        return new ResponseEntity<>(
                companyService.find(filter, pageable),
                HttpStatus.OK);
    }

    @PostMapping
    public CompanyReadDto create(@RequestBody @Validated CompanyCreateUpdateDto company) {
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    public Optional<CompanyReadDto> update(@PathVariable Long id, @RequestBody @Validated CompanyCreateUpdateDto company) {
        return companyService.update(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!companyService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
