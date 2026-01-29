package ru.ekononov.phonebook.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.common.company.CompanyTestFactory;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;
import ru.ekononov.phonebook.mapper.company.CompanyMapper;
import ru.ekononov.phonebook.mapper.company.CompanyReadDtoMapper;
import ru.ekononov.phonebook.service.company.CompanyServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private CompanyReadDtoMapper companyReadDtoMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() {
        Company oracleCompany = CompanyTestFactory.oracleCompany();

        doReturn(Optional.of(oracleCompany))
                .when(companyRepository)
                .findById(Oracle.ID);

        doReturn(CompanyTestFactory.oracleCompanyReadDto())
                .when(companyReadDtoMapper)
                .map(oracleCompany);

        CompanyReadDto foundCompany = companyService.findById(Oracle.ID);

        assertNotNull(foundCompany);
        assertEquals(Oracle.ID, foundCompany.id());
        assertEquals(Oracle.NAME, foundCompany.name());

        verify(companyRepository, times(1)).findById(Oracle.ID);
        verify(companyReadDtoMapper, times(1)).map(oracleCompany);
        verifyNoMoreInteractions(companyRepository, companyMapper, companyReadDtoMapper);
    }


    @Test
    void whenFindByIdNotExistingCompany_thenThrowsException() {
        doReturn(Optional.empty())
                .when(companyRepository)
                .findById(NOT_EXISTING_ID);

        assertThrows(ResponseStatusException.class, () -> companyService.findById(NOT_EXISTING_ID));

        verify(companyRepository, times(1)).findById(NOT_EXISTING_ID);
        verifyNoMoreInteractions(companyRepository, companyMapper, companyReadDtoMapper);
    }

    @Test
    void whenSaveCompany_thenReturnSavedCompany() {
        Company oracleCompany = CompanyTestFactory.oracleCompany();

        doReturn(oracleCompany)
                .when(companyRepository)
                .save(oracleCompany);

        doReturn(CompanyTestFactory.oracleCompanyReadDto())
                .when(companyReadDtoMapper)
                .map(oracleCompany);

        CompanyCreateUpdateDto oracleCompanyCreateUpdateDto = CompanyTestFactory.oracleCompanyCreateUpdateDto();

        doReturn(oracleCompany)
                .when(companyMapper)
                .map(oracleCompanyCreateUpdateDto);

        CompanyReadDto createdCompany = companyService.create(oracleCompanyCreateUpdateDto);

        assertNotNull(createdCompany);
        assertEquals(Oracle.ID, createdCompany.id());
        assertEquals(Oracle.NAME, createdCompany.name());

        verify(companyRepository, times(1)).save(oracleCompany);
        verify(companyMapper, times(1)).map(oracleCompanyCreateUpdateDto);
        verify(companyReadDtoMapper, times(1)).map(oracleCompany);
        verifyNoMoreInteractions(companyRepository, companyMapper, companyReadDtoMapper);
    }
}
