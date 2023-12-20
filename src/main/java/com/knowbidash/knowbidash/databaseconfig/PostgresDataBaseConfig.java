package com.knowbidash.knowbidash.databaseconfig;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresEntityManagerFactory",
        basePackages = {
                "com.knowbidash.knowbidash.repositories.postgres.repoUser",
                "com.knowbidash.knowbidash.repositories.postgres.repoRoles",
                "com.knowbidash.knowbidash.repositories.postgres.repoRefreshToken"
        },
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDataBaseConfig {
    @Autowired
    Environment env;

    @Primary
    @Bean(name = "postgresDataSource")
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(env.getProperty("postgres.datasource.url"));
        ds.setUsername(env.getProperty("postgres.datasource.username"));
        ds.setPassword(env.getProperty("postgres.datasource.password"));
        ds.setDriverClassName(env.getProperty("postgres.datasource.driver-class-name"));
        return ds;
    }

    @Primary
    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager(){
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        HashMap<String,Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        bean.setJpaPropertyMap(properties);
        bean.setPackagesToScan(
                "com.knowbidash.knowbidash.entities.postgres.user",
                "com.knowbidash.knowbidash.entities.postgres.role",
                "com.knowbidash.knowbidash.entities.postgres.refreshToken",
                "com.knowbidash.knowbidash.security.payload.request"
        );
        return bean;
    }

    @Primary
    @Bean("postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory postgresManagerFactory){
        return new JpaTransactionManager(postgresManagerFactory);
    }
}
