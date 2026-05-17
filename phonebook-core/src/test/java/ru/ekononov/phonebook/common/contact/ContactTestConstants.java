package ru.ekononov.phonebook.common.contact;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class ContactTestConstants {
    public long NOT_EXISTING_ID = 35L;
    public String INVALID_EMAIL = "invalidEmail.com";

    @UtilityClass
    public static class DefaultContact {
        public final String FIRST_NAME = "Default";
        public final String PHONE_NUMBER = "81231234567";
        public final String EMAIL = "default@gmail.com";
    }

    @UtilityClass
    public static class Ivan {
        public final long ID = 1;
        public final String FIRST_NAME = "Ivan";
        public final String LAST_NAME = "Ivanov";
        public final String PHONE_NUMBER = "89131234567";
        public final LocalDate BIRTH_DATE = LocalDate.of(1990,1,10);
        public final String EMAIL = "ivan@gmail.com";
        public final String UPDATED_EMAIL = "ivan@yandex.ru";
    }

    @UtilityClass
    public static class Petr {
        public final long ID = 2;
        public final String FIRST_NAME = "Petr";
        public final String LAST_NAME = "Petrov";
        public final String PHONE_NUMBER = "89171234568";
        public final LocalDate BIRTH_DATE = LocalDate.of(1995,10,19);
        public final String EMAIL = "petr@gmail.com";
    }

    @UtilityClass
    public static class Sveta {
        public final long ID = 3;
        public final String FIRST_NAME = "Sveta";
        public final String LAST_NAME = "Svetikova";
        public final String PHONE_NUMBER = "89141234569";
        public final LocalDate BIRTH_DATE = LocalDate.of(2001,12,23);
        public final String EMAIL = "sveta@gmail.com";
    }

    @UtilityClass
    public static class Vlad {
        public final long ID = 4;
        public final String FIRST_NAME = "Vlad";
        public final String LAST_NAME = "Vladikov";
        public final String PHONE_NUMBER = "89161234510";
        public final LocalDate BIRTH_DATE = LocalDate.of(1984, 3, 14);
        public final String EMAIL = "vlad@gmail.com";
    }

    @UtilityClass
    public static class Anton {
        public final long ID = 5;
        public final String FIRST_NAME = "Anton";
        public final String LAST_NAME = "Antonov";
        public final String PHONE_NUMBER = "89191234511";
        public final LocalDate BIRTH_DATE = LocalDate.of(1983,5,18);
        public final String EMAIL = "anton@gmail.com";
    }

    @UtilityClass
    public static class Egor {
        public final long ID = 6;
        public final String FIRST_NAME = "Egor";
        public final String LAST_NAME = "Kononov";
        public final String PHONE_NUMBER = "8916234567";
        public final LocalDate BIRTH_DATE = LocalDate.of(1988,6,7);
        public final String EMAIL = "egor@gmail.com";
    }
}
