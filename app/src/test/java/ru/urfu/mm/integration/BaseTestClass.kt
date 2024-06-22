package ru.urfu.mm.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import ru.urfu.mm.TestConfiguration

/**
 * Базовый класс для всех интеграционных тестов
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestClass {
    @LocalServerPort
    protected var port: Int = 0
    @Autowired
    private lateinit var configuration: TestConfiguration

    /**
     * Получить адрес локального сервера
     */
    protected fun address(): String {
        return configuration.address + port
    }
}
