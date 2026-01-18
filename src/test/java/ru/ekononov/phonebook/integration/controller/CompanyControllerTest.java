package ru.ekononov.phonebook.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ekononov.phonebook.common.company.CompanyTestConstants;
import ru.ekononov.phonebook.common.company.CompanyTestFactory;
import ru.ekononov.phonebook.dto.company.CompanyCreateUpdateDto;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class CompanyControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void whenFindByIdExistingCompany_thenReturnCompany() throws Exception {
        mockMvc.perform(get("/api/v1/company/{id}", Google.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Google.ID))
                .andExpect(jsonPath("$.name").value(Google.NAME));
    }

    @Test
    void whenFindByIdNotExistingCompany_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/company/{id}", CompanyTestConstants.NOT_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindAll_thenReturnAllCompanies() throws Exception {
        mockMvc.perform(get("/api/v1/company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.content[?(@.id==1)].name").value(Google.NAME))
                .andExpect(jsonPath("$.content[?(@.id==2)].name").value(Amazon.NAME))
                .andExpect(jsonPath("$.content[?(@.id==3)].name").value(Apple.NAME));
    }

    @Test
    void whenCreateValidCompany_thenReturnCreatedCompany() throws Exception {
        mockMvc.perform(post("/api/v1/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CompanyTestFactory.oracleCompanyCreateUpdateDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(Oracle.NAME));
    }

    @Test
    void whenCreateEmptyNameCompany_thenReturnError() throws Exception {
        mockMvc.perform(post("/api/v1/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CompanyTestFactory.emptyNameCompanyCreateUpdateDto())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value(CompanyCreateUpdateDto.Fields.name))
                .andExpect(jsonPath("$.errors[0].message").value("must not be blank"));
    }

    @Test
    void whenUpdateValidCompany_thenReturnUpdatedCompany() throws Exception {
        mockMvc.perform(put("/api/v1/company/{id}", Google.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(CompanyTestFactory.updatedGoogleCompanyCreateUpdateDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Google.ID))
                .andExpect(jsonPath("$.name").value(Google.UPDATED_NAME));
    }

    @Test
    void whenDeleteExistingCompany_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/company/{id}", Apple.ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteNotExistingCompany_thenReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/company/{id}", NOT_EXISTING_ID))
                .andExpect(status().isNotFound());
    }
}