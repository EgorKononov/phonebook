package ru.ekononov.phonebook.common.company;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CompanyTestConstants {
    public long NOT_EXISTING_ID = 35L;

    @UtilityClass
    public static class Google {
        public final long ID = 1;
        public final String NAME = "Google";
        public final String UPDATED_NAME = "Google updated name";
    }

    @UtilityClass
    public static class Amazon {
        public final long ID = 2;
        public final String NAME = "Amazon";
    }

    @UtilityClass
    public static class Apple {
        public final long ID = 3;
        public final String NAME = "Apple";
    }

    @UtilityClass
    public static class Oracle {
        public final long ID = 4;
        public final String NAME = "Oracle";
    }
}
