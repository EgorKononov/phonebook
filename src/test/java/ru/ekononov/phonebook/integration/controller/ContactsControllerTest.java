package ru.ekononov.phonebook.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ekononov.phonebook.common.contact.ContactTestConstants;
import ru.ekononov.phonebook.common.contact.ContactTestFactory;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.dto.contact.ContactFilter;
import ru.ekononov.phonebook.integration.IntegrationTestBase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.ekononov.phonebook.common.company.CompanyTestConstants.*;
import static ru.ekononov.phonebook.common.contact.ContactTestConstants.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class ContactsControllerTest extends IntegrationTestBase {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Test
    void whenFindByIdExistingContact_thenReturnContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts/{id}", Ivan.ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Ivan.ID))
                .andExpect(jsonPath("$.firstName").value(Ivan.FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(Ivan.LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(Ivan.PHONE_NUMBER))
                .andExpect(jsonPath("$.birthDate").value(Ivan.BIRTH_DATE.toString()))
                .andExpect(jsonPath("$.email").value(Ivan.EMAIL))
                .andExpect(jsonPath("$.company.id").value(Google.ID))
                .andExpect(jsonPath("$.company.name").value(Google.NAME));
    }

    @Test
    void whenFindByIdNotExistingContact_thenReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/contacts/{id}", ContactTestConstants.NOT_EXISTING_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenFindAll_thenReturnAllContacts() throws Exception {
        mockMvc.perform(get("/api/v1/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(5))
                .andExpect(jsonPath("$.content[?(@.id==1)].phoneNumber").value(Ivan.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==2)].phoneNumber").value(Petr.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==3)].phoneNumber").value(Sveta.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==4)].phoneNumber").value(Vlad.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==5)].phoneNumber").value(Anton.PHONE_NUMBER));
    }

    @Test
    void whenFindAllWithFilterCompleteMatch_thenReturnFoundContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts").param(ContactFilter.Fields.firstName, Ivan.FIRST_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[?(@.id==1)].phoneNumber").value(Ivan.PHONE_NUMBER));
    }

    @Test
    void whenFindAllWithFilterPartialMatch_thenReturnFoundContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts").param(ContactFilter.Fields.firstName, "an"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[?(@.id==1)].phoneNumber").value(Ivan.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==5)].phoneNumber").value(Anton.PHONE_NUMBER));
    }

    @Test
    void whenFindAllWithFilterDifferentCase_thenReturnFoundContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts").param(ContactFilter.Fields.firstName, "ivan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[?(@.id==1)].phoneNumber").value(Ivan.PHONE_NUMBER));
    }

    @Test
    void whenFindAllWithFilterByDate_thenReturnFoundContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts").param(ContactFilter.Fields.birthDate, "2000-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect(jsonPath("$.content[?(@.id==1)].phoneNumber").value(Ivan.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==2)].phoneNumber").value(Petr.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==4)].phoneNumber").value(Vlad.PHONE_NUMBER))
                .andExpect(jsonPath("$.content[?(@.id==5)].phoneNumber").value(Anton.PHONE_NUMBER));
    }

    @Test
    void whenFindAllWithPageSizeParameter_thenReturnPageWithSetSize() throws Exception {
        mockMvc.perform(get("/api/v1/contacts")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void whenFindAllWithPageParameter_thenReturnPageWithSetNumber() throws Exception {
        mockMvc.perform(get("/api/v1/contacts")
                        .param("page", "1")
                        .param("size", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(3))
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    void whenFindAllWithSort_thenReturnSortedContacts() throws Exception {
        mockMvc.perform(get("/api/v1/contacts")
                        .param("sort", ContactFilter.Fields.lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(5))
                .andExpect(jsonPath("$.content[0].lastName").value(Anton.LAST_NAME))
                .andExpect(jsonPath("$.content[1].lastName").value(Ivan.LAST_NAME))
                .andExpect(jsonPath("$.content[2].lastName").value(Petr.LAST_NAME))
                .andExpect(jsonPath("$.content[3].lastName").value(Sveta.LAST_NAME))
                .andExpect(jsonPath("$.content[4].lastName").value(Vlad.LAST_NAME));
    }

    @Test
    void whenCreateValidContact_thenReturnCreatedContact() throws Exception {
        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ContactTestFactory.egorContactCreateUpdateDto())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(Egor.FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(Egor.LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(Egor.PHONE_NUMBER))
                .andExpect(jsonPath("$.birthDate").value(Egor.BIRTH_DATE.toString()))
                .andExpect(jsonPath("$.email").value(Egor.EMAIL))
                .andExpect(jsonPath("$.company.id").value(Google.ID))
                .andExpect(jsonPath("$.company.name").value(Google.NAME));
    }

    @Test
    void whenCreateEmptyPhoneNumberContact_thenReturnError() throws Exception {
        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ContactTestFactory.emptyPhoneNumberContactCreateUpdateDto())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value(ContactCreateUpdateDto.Fields.phoneNumber))
                .andExpect(jsonPath("$.errors[0].message").value("must not be blank"));
    }

    @Test
    void whenCreateInvalidEmailContact_thenReturnError() throws Exception {
        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ContactTestFactory.invalidEmailContactCreateUpdateDto())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("email"))
                .andExpect(jsonPath("$.errors[0].message").value("must be a well-formed email address"));

    }

    @Test
    void whenCreateInvalidContactNameContact_thenReturnError() throws Exception {
        mockMvc.perform(post("/api/v1/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ContactTestFactory.invalidContactNameContactCreateUpdateDto())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("firstName or lastName should be filled in"));
    }

    @Test
    void whenUpdateValidContact_thenReturnUpdatedContact() throws Exception {
        mockMvc.perform(put("/api/v1/contacts/{id}", Ivan.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ContactTestFactory.updatedIvanContactCreateUpdateDto())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(Ivan.ID))
                .andExpect(jsonPath("$.firstName").value(Ivan.FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(Ivan.LAST_NAME))
                .andExpect(jsonPath("$.phoneNumber").value(Ivan.PHONE_NUMBER))
                .andExpect(jsonPath("$.birthDate").value(Ivan.BIRTH_DATE.toString()))
                .andExpect(jsonPath("$.email").value(Ivan.UPDATED_EMAIL))
                .andExpect(jsonPath("$.company.id").value(Google.ID))
                .andExpect(jsonPath("$.company.name").value(Google.NAME));
    }

    @Test
    void whenDeleteExistingContact_thenReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/contacts/{id}", Petr.ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteNotExistingContact_thenReturnNotFound() throws Exception {
        mockMvc.perform(delete("/api/v1/contacts/{id}", ContactTestConstants.NOT_EXISTING_ID))
                .andExpect(status().isNotFound());
    }
}