package ru.ekononov.phonebook.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.common.company.CompanyTestFactory;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;
import ru.ekononov.phonebook.integration.IntegrationTestBase;
import ru.ekononov.phonebook.service.company.CompanyService;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;

@RequiredArgsConstructor
public class CompanyServiceImplTest extends IntegrationTestBase {
    private final CompanyService companyService;

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() {
        CompanyReadDto foundCompany = companyService.findById(Apple.ID);

        assertNotNull(foundCompany);
        assertEquals(Apple.NAME, foundCompany.name());
    }

    @Test
    void whenFindByIdNotExistingCompany_thenThrowsException() {
        assertThrows(ResponseStatusException.class, () -> companyService.findById(NOT_EXISTING_ID));
    }

    @Test
    void whenSaveCompany_thenReturnSavedCompany() {
        CompanyReadDto createdCompany = companyService.create(CompanyTestFactory.oracleCompanyCreateUpdateDto());
        CompanyReadDto foundCompany = companyService.findById(createdCompany.id());

        assertNotNull(foundCompany);
        assertEquals(Oracle.NAME, foundCompany.name());
    }

    @Test
    void whenDeleteExistingCompany_thenReturnTrueAndDeletedCompanyNotFound() {
        assertTrue(companyService.delete(Apple.ID));
        assertThrows(ResponseStatusException.class, () -> companyService.findById(Apple.ID));
    }
}
