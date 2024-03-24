package ru.urfu.mm.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.urfu.mm.applicationlegacy.gateway.LoggerGateway;
import ru.urfu.mm.applicationlegacy.gateway.PasswordGateway;
import ru.urfu.mm.applicationlegacy.gateway.TokenGateway;
import ru.urfu.mm.applicationlegacy.gateway.UserGateway;
import ru.urfu.mm.applicationlegacy.usecase.CreateAdministrator;

@Configuration
public class UseCaseConfiguration {
    @Bean
    public CreateAdministrator createAdministrator(
            TokenGateway tokenGateway,
            LoggerGateway loggerGateway,
            PasswordGateway passwordGateway,
            UserGateway userGateway) {
        return new CreateAdministrator(tokenGateway, loggerGateway, passwordGateway, userGateway);
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
