package ru.domclick.account.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {
        "ru.domclick.account.domain"
})
@EnableJpaRepositories(basePackages = {
        "ru.domclick.account.repository"
})
public class JpaConfiguration {

}
