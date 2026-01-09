package ru.ekononov.phonebook.integration.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;
import ru.ekononov.phonebook.integration.IntegrationTestBase;
import ru.ekononov.phonebook.service.company.CompanyService;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class CompanyServiceImplTest extends IntegrationTestBase {
    private final CompanyService companyService;
    private final String oracleName = "Oracle";
    private final CompanyCreateUpdateDto oracleCompany = new CompanyCreateUpdateDto(oracleName);

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany(){
        CompanyReadDto foundCompany = companyService.findById(3L);

        assertNotNull(foundCompany);
        assertEquals("Apple", foundCompany.name());
    }

    @Test
    void whenFindByIdNotExistingCompany_thenThrowsException() {
        long notExistingId = 355235235L;
        assertThrows(ResponseStatusException.class, () ->  companyService.findById(notExistingId));
    }

    @Test
    void whenSaveCompany_thenFindSavedCompany() {
        CompanyReadDto createdCompany = companyService.create(oracleCompany);
        CompanyReadDto foundCompany = companyService.findById(createdCompany.id());

        assertNotNull(foundCompany);
        assertEquals(oracleName, foundCompany.name());
    }
    
    @Test
    void whenDeleteExistingCompany_thenReturnTrueAndDeletedCompanyNotFound() {
        long companyId = 3L;
        assertTrue(companyService.delete(companyId));
        assertThrows(ResponseStatusException.class, () ->  companyService.findById(companyId));
    }
}
