package ru.ekononov.phonebook.service.company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.ekononov.phonebook.mapper.company.CompanyMapper;
import ru.ekononov.phonebook.mapper.company.CompanyReadDtoMapper;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.database.querydsl.QPredicates;
import ru.ekononov.phonebook.database.repository.CompanyRepository;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyFilter;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;

import java.util.Optional;

import static ru.ekononov.phonebook.database.entity.QCompany.company;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final CompanyReadDtoMapper companyReadDtoMapper;

    @Override
    public CompanyReadDto findById(long id) {
        return companyRepository.findById(id)
                .map(companyReadDtoMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Page<CompanyReadDto> find(CompanyFilter filter, Pageable pageable) {
        return companyRepository.findAll(
                        QPredicates.builder()
                                .add(filter.name(), company.name::containsIgnoreCase)
                                .build(),
                        pageable)
                .map(companyReadDtoMapper::map);
    }

    @Override
    @Transactional
    public CompanyReadDto create(CompanyCreateUpdateDto company) {
        Company savedCompany = companyRepository.save(companyMapper.map(company));
        log.info("Company added {}", savedCompany);

        return companyReadDtoMapper.map(savedCompany);
    }

    @Override
    @Transactional
    public Optional<CompanyReadDto> update(Long id, CompanyCreateUpdateDto company) {
        return companyRepository.findById(id)
                .map(companyToUpdate -> companyMapper.map(company, companyToUpdate))
                .map(companyRepository::saveAndFlush)
                .map(companyReadDtoMapper::map);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return companyRepository.findById(id)
                .map(company -> {
                    companyRepository.delete(company);
                    companyRepository.flush();

                    return true;
                })
                .orElse(false);
    }
}
