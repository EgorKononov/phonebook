package ru.ekononov.phonebook.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.ekononov.phonebook.validation.validator.ContactNameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ContactNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactName {

    String message() default "firstName or lastName should be filled in";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}