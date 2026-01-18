package ru.ekononov.phonebook.common.company;

import lombok.experimental.UtilityClass;
import ru.ekononov.phonebook.database.entity.Company;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.dto.company.CompanyReadDto;

import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;

@UtilityClass
public class CompanyTestFactory {
    public Company oracleCompany() {
        return Company.builder()
                .name(Oracle.NAME)
                .build();
    }

    public CompanyReadDto oracleCompanyReadDto() {
        return new CompanyReadDto(Oracle.ID, Oracle.NAME);
    }

    public CompanyCreateUpdateDto oracleCompanyCreateUpdateDto() {
        return new CompanyCreateUpdateDto(Oracle.NAME);
    }

    public static CompanyCreateUpdateDto emptyNameCompanyCreateUpdateDto() {
        return new CompanyCreateUpdateDto("");
    }

    public static CompanyCreateUpdateDto updatedGoogleCompanyCreateUpdateDto() {
        return new CompanyCreateUpdateDto(Google.UPDATED_NAME);
    }
}
