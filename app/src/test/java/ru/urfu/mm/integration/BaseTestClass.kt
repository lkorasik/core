package ru.urfu.mm.integration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.urfu.mm.TestConfiguration

/**
 * Базовый класс для всех интеграционных тестов
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTestClass {
    @Autowired
    protected lateinit var configuration: TestConfiguration
}
