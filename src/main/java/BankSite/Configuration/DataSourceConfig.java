package BankSite.Configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:ORCL");
        dataSource.setUsername("XFragosoBankSite");
        dataSource.setPassword("password123");

        return dataSource;
    }
}
