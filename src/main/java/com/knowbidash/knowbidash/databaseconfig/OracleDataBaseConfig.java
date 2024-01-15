package com.knowbidash.knowbidash.databaseconfig;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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
        entityManagerFactoryRef = "oracleManagerFactory",
        basePackages 	 = {"com.knowbidash.knowbidash.repositories.oracle.repoAtendimentoPacienteV"},
        transactionManagerRef = "oracleTransactionManager"
)
public class OracleDataBaseConfig {
    @Autowired
    Environment env;

    @Bean(name= "oracleDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("oracle.datasource.url"));
        config.setUsername(env.getProperty("oracle.datasource.username"));
        config.setPassword(env.getProperty("oracle.datasource.password"));
        config.setMaximumPoolSize(7);
        config.setIdleTimeout(30000);
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(3000);
        config.setMaxLifetime(600000);
        return new HikariDataSource(config);
    }

    @Bean(name= "oracleManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        HashMap<String,Object> properties = new HashMap<String, Object>();
        bean.setJpaPropertyMap(properties);
        bean.setPackagesToScan(
                "com.knowbidash.knowbidash.entities.oracle.atendimentoPaciente",
                "com.knowbidash.knowbidash.streaming.JSONDataService",
                "com.knowbidash.knowbidash.streaming.websocket");
        bean.setJpaPropertyMap(properties);
        return bean;
    }

    @Bean("oracleTransactionManager")
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleManagerFactory") EntityManagerFactory oracleManagerFactory ) {
        return new JpaTransactionManager(oracleManagerFactory);
    }

}
