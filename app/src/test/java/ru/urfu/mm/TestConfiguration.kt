package ru.urfu.mm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для тестирования
 */
@Configuration
public class TestConfiguration {
    /**
     * Адрес сервера
     */
    @Value("${app.address}")
    private String address;

    public String getAddress() {
        return address;
    }
}
