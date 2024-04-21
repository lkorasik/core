package ru.urfu.mm.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Базовый класс для всех интеграционных тестов
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractTest {
    @LocalServerPort
    protected int port;

    /**
     * Получить адрес сервера
     */
    protected String address() {
        return "http://localhost:" + port;
    }
}
