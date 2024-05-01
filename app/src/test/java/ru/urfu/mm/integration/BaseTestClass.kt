package ru.urfu.mm.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

/**
 * Базовый класс для всех интеграционных тестов
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestClass {
    @LocalServerPort
    protected var port: Int = 0

    /**
     * Получить адрес локального сервера
     */
    protected fun address(): String {
        return "http://localhost:$port"
    }
}
