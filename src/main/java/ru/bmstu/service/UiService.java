package ru.bmstu.service;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public interface UiService {
    void start() throws CsvValidationException, IOException;
    void mainMenu() throws CsvValidationException, IOException;
    void addUSer();
    void deleteUser();
    void updateStudent();
}
