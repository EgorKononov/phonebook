package ru.ekononov.phonebook.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
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

    private final long oracleId = 3L;
    private final String oracleName = "Oracle";

    private Company oracleCompany;
    private CompanyReadDto oracleCompanyReadDto;
    private CompanyCreateUpdateDto oracleCompanyCreateUpdateDto;

    @BeforeEach
    void setUp() {
        oracleCompany = new Company(oracleId, oracleName);
        oracleCompanyReadDto = new CompanyReadDto(oracleId, oracleName);
        oracleCompanyCreateUpdateDto = new CompanyCreateUpdateDto(oracleName);
    }

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() {
        doReturn(Optional.of(oracleCompany))
                .when(companyRepository)
                .findById(oracleId);

        doReturn(oracleCompanyReadDto)
                .when(companyReadDtoMapper)
                .map(oracleCompany);

        CompanyReadDto foundCompany = companyService.findById(oracleId);

        assertNotNull(foundCompany);
        assertEquals(oracleName, foundCompany.name());

        verify(companyRepository, times(1)).findById(oracleId);
        verify(companyReadDtoMapper, times(1)).map(oracleCompany);
        verifyNoMoreInteractions(companyRepository,companyMapper,companyReadDtoMapper);
    }


    @Test
    void whenFindByIdNotExistingCompany_thenThrowsException() {
        long notExistingId = 355235235L;

        doReturn(Optional.empty())
                .when(companyRepository)
                .findById(notExistingId);

        assertThrows(ResponseStatusException.class, () -> companyService.findById(notExistingId));

        verify(companyRepository, times(1)).findById(notExistingId);
        verifyNoMoreInteractions(companyRepository,companyMapper,companyReadDtoMapper);
    }

    @Test
    void whenSaveCompany_thenReturnSavedCompany() {
        doReturn(oracleCompany)
                .when(companyRepository)
                .save(oracleCompany);

        doReturn(oracleCompanyReadDto)
                .when(companyReadDtoMapper)
                .map(oracleCompany);

        doReturn(oracleCompany)
                .when(companyMapper)
                .map(oracleCompanyCreateUpdateDto);

        CompanyReadDto createdCompany = companyService.create(oracleCompanyCreateUpdateDto);

        assertNotNull(createdCompany);
        assertEquals(oracleId, createdCompany.id());
        assertEquals(oracleName, createdCompany.name());

        verify(companyRepository, times(1)).save(oracleCompany);
        verify(companyMapper, times(1)).map(oracleCompanyCreateUpdateDto);
        verify(companyReadDtoMapper, times(1)).map(oracleCompany);
        verifyNoMoreInteractions(companyRepository,companyMapper,companyReadDtoMapper);
    }
}
