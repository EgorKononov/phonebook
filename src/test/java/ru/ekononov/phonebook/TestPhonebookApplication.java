package ru.ekononov.phonebook;

import org.springframework.boot.SpringApplication;

public class TestPhonebookApplication {

    public static void main(String[] args) {
        SpringApplication.from(PhonebookApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
