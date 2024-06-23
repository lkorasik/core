package ru.urfu.mm

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.annotation.Configuration

/**
 * Конфигурация для тестирования
 */
@Configuration
class TestConfiguration {
    /**
     * Доменное имя сервера. Например `localhost`
     */
    @Value("\${app.server.domain}")
    private lateinit var domain: String

    /**
     * Протокол сервера. Например `http`
     */
    @Value("\${app.server.protocol}")
    private lateinit var protocol: String

    @Autowired
    private lateinit var serverContext: ServletWebServerApplicationContext

    /**
     * Адрес локального сервера
     */
    fun address(): String {
        val port = serverContext.webServer.port
        return "$protocol://$domain:$port"
    }
}
