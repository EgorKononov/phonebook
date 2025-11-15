package ru.ekononov.phonebook.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ekononov.phonebook.dto.contact.ContactCreateUpdateDto;
import ru.ekononov.phonebook.validation.ContactName;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class ContactNameValidator implements ConstraintValidator<ContactName, ContactCreateUpdateDto> {
    @Override
    public boolean isValid(ContactCreateUpdateDto value, ConstraintValidatorContext context) {
        return hasText(value.firstName()) || hasText(value.lastName());
    }
}
