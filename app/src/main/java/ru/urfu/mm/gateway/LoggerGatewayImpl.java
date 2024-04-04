package ru.urfu.mm.gateway;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.urfu.mm.application.gateway.LoggerGateway;

@Component
public class LoggerGatewayImpl implements LoggerGateway {
    private final Logger logger;

    @Autowired
    public LoggerGatewayImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
