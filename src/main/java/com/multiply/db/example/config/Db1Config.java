package com.multiply.db.example.config;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.multiply.db.example.entity.db1",
        entityManagerFactoryRef = "entityManagerFactoryDb1",
        transactionManagerRef = "transactionManagerDb1"
)
public class Db1Config {

    @Primary
    @Bean(name = "dataSourceDb1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource dataSourceDb1() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactoryDb1")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryDb1(
            @Qualifier("dataSourceDb1") DataSource dataSource,
            JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.multiply.db.example.entity.db1");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // Especifica el dialecto
        //jpaProperties.getProperties().put("hibernate.dialect", "org.hibernate.dialect.Oracle11cDialect");
        em.setJpaPropertyMap(jpaProperties.getProperties());

        return em;
    }

    @Primary
    @Bean(name = "transactionManagerDb1")
    public PlatformTransactionManager transactionManagerDb1(
            @Qualifier("entityManagerFactoryDb1") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
