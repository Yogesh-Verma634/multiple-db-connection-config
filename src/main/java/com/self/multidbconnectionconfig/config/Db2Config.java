package com.self.multidbconnectionconfig.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.self.multidbconnectionconfig.repository.db2",
        entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "db2PlatformTransactionManager")
public class Db2Config {

    @Bean
    @ConfigurationProperties("spring.db2.datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource db2DataSource(@Qualifier("db2DataSourceProperties") DataSourceProperties db2DataSourceProperties) {
        return db2DataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(@Qualifier("db2DataSource") DataSource db2DataSource, EntityManagerFactoryBuilder builder) {
       return builder
                .dataSource(db2DataSource)
                .packages("com.self.multidbconnectionconfig.model.db2")
                .persistenceUnit("db2")
                .build();

    }

    @Bean
    public PlatformTransactionManager db2PlatformTransactionManager(EntityManagerFactory db2EntityManagerFactory) {
        return new JpaTransactionManager(db2EntityManagerFactory);
    }

}
