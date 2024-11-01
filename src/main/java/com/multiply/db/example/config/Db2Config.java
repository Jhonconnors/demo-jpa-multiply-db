package com.multiply.db.example.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.multiply.db.example.entity.db2", // Paquete donde están los repositorios
        entityManagerFactoryRef = "entityManagerFactoryDb2",
        transactionManagerRef = "transactionManagerDb2"
)
public class Db2Config {

    @Bean(name = "dataSourceDb2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSourceDb2() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactoryDb2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDb2(
            @Qualifier("dataSourceDb2") DataSource dataSource,
            JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.multiply.db.example.entity.db2");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Especifica el dialecto para SQL Server
        jpaProperties.getProperties().put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        em.setJpaPropertyMap(jpaProperties.getProperties());

        return em;
    }

    @Bean(name = "transactionManagerDb2")
    public PlatformTransactionManager transactionManagerDb2(
            @Qualifier("entityManagerFactoryDb2") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}