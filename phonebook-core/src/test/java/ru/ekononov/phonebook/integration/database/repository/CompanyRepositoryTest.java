package ru.ekononov.phonebook.integration.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.ekononov.phonebook.common.company.CompanyTestFactory;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;

@RequiredArgsConstructor
class CompanyRepositoryTest extends IntegrationTestBase {
    private final CompanyRepository companyRepository;

    @Test
    void whenFindAll_thenReturnAllCompanies() {
        List<Company> allCompanies = companyRepository.findAll();

        assertNotNull(allCompanies);
        assertEquals(3, allCompanies.size());

        Optional<Company> googleCompanyOptional = allCompanies.stream()
                .filter(company -> company.getId() == Google.ID)
                .findFirst();

        assertTrue(googleCompanyOptional.isPresent());
        Company googleCompany = googleCompanyOptional.get();
        assertEquals(Google.NAME, googleCompany.getName());

        Optional<Company> amazonCompanyOptional = allCompanies.stream()
                .filter(company -> company.getId() == Amazon.ID)
                .findFirst();

        assertTrue(amazonCompanyOptional.isPresent());
        Company amazonCompany = amazonCompanyOptional.get();
        assertEquals(Amazon.NAME, amazonCompany.getName());
    }

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() {
        Optional<Company> foundCompanyOptional = companyRepository.findById(Apple.ID);

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();
        assertEquals(Apple.NAME, foundCompany.getName());
    }

    @Test
    void whenFindByIdNotExistingCompany_thenReturnEmptyOptional() {
        Optional<Company> foundCompanyOptional = companyRepository.findById(NOT_EXISTING_ID);

        assertTrue(foundCompanyOptional.isEmpty());
    }

    @Test
    void whenSaveCompany_thenFindSavedCompany() {
        Company savedCompany = companyRepository.save(CompanyTestFactory.oracleCompany());
        Optional<Company> foundCompanyOptional = companyRepository.findById(Oracle.ID);

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();
        assertEquals(Oracle.NAME, foundCompany.getName());
        assertEquals(savedCompany, foundCompany);
    }

    @Test
    void whenDeleteExistingCompany_thenDeletedCompanyNotFound() {
        Optional<Company> foundCompanyOptional = companyRepository.findById(Apple.ID);

        assertTrue(foundCompanyOptional.isPresent());
        Company foundCompany = foundCompanyOptional.get();

        companyRepository.delete(foundCompany);
        companyRepository.flush();

        Optional<Company> deletedCompanyOptional = companyRepository.findById(Apple.ID);
        assertTrue(deletedCompanyOptional.isEmpty());
    }
}