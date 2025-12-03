package ru.ekononov.phonebook.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase {
    private final CompanyRepository companyRepository;

    private final String oracleName = "Oracle";
    private final Company oracleCompany = Company.builder()
            .name(oracleName)
            .build();

    @Test
    void whenFindAll_thenReturnAllCompany() {
        List<Company> allCompanies = companyRepository.findAll();

        assertNotNull(allCompanies);
        assertEquals(3, allCompanies.size());

        Optional<Company> googleCompanyOptional = allCompanies.stream()
                .filter(company -> company.getId() == 1)
                .findFirst();

        assertTrue(googleCompanyOptional.isPresent());
        Company googleCompany = googleCompanyOptional.get();
        assertEquals("Google", googleCompany.getName());

        Optional<Company> amazonCompanyOptional = allCompanies.stream()
                .filter(company -> company.getId() == 2)
                .findFirst();

        assertTrue(amazonCompanyOptional.isPresent());
        Company amazonCompany = amazonCompanyOptional.get();
        assertEquals("Amazon", amazonCompany.getName());
    }

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() {
        Optional<Company> foundCompanyOptional = companyRepository.findById(3L);

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();
        assertEquals("Apple", foundCompany.getName());
    }

    @Test
    void whenFindByIdNotExistingCompany_thenReturnEmptyOptional() {
        long notExistingId = 355235235L;
        Optional<Company> foundCompanyOptional = companyRepository.findById(notExistingId);

        assertTrue(foundCompanyOptional.isEmpty());
    }

    @Test
    void whenSaveCompany_thenFindSavedCompany() {
        Company savedCompany = companyRepository.save(oracleCompany);
        Optional<Company> foundCompanyOptional = companyRepository.findById(oracleCompany.getId());

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();
        assertEquals(oracleName, foundCompany.getName());
        assertEquals(savedCompany, foundCompany);
    }

    @Test
    void whenDeleteExistingCompany_thenDeletedCompanyNotFound() {
        Optional<Company> foundCompanyOptional = companyRepository.findById(3L);

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();

        companyRepository.delete(foundCompany);
        companyRepository.flush();

        Optional<Company> deletedCompanyOptional = companyRepository.findById(3L);
        assertTrue(deletedCompanyOptional.isEmpty());
    }
}