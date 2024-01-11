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
        ds.setUrl(env.getProperty("oracle.datasource.url"));
        ds.setUsername(env.getProperty("oracle.datasource.username"));
        ds.setPassword(env.getProperty("oracle.datasource.password"));
        return ds;
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
        return bean;
    }

    @Bean("oracleTransactionManager")
    public PlatformTransactionManager oracleTransactionManager(@Qualifier("oracleManagerFactory") EntityManagerFactory oracleManagerFactory ) {
        return new JpaTransactionManager(oracleManagerFactory);
    }

}
