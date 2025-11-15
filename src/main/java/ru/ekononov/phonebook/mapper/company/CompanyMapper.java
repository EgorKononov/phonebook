package ru.ekononov.phonebook.mapper.company;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.mapper.Mapper;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;

@Component
@RequiredArgsConstructor
public class CompanyMapper implements Mapper<CompanyCreateUpdateDto, Company> {
    @Override
    public Company map(CompanyCreateUpdateDto source) {
        Company company = new Company();
        copy(source, company);

        return company;
    }

    @Override
    public Company map(CompanyCreateUpdateDto fromObject, Company toObject) {
        copy(fromObject, toObject);

        return toObject;
    }

    private void copy(CompanyCreateUpdateDto source, Company company) {
        company.setName(source.name());
    }
}
