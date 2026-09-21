package com.digitalhealth.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    DataSource dataSource(
            @Value("${DATABASE_URL:}") String databaseUrl,
            @Value("${DB_URL:jdbc:h2:file:./data/digital_health_records;MODE=MySQL;AUTO_SERVER=TRUE}") String dbUrl,
            @Value("${DB_DRIVER:org.h2.Driver}") String driver,
            @Value("${DB_USERNAME:sa}") String username,
            @Value("${DB_PASSWORD:}") String password) {
        if (!databaseUrl.isBlank()) {
            URI uri = URI.create(databaseUrl);
            String[] credentials = uri.getRawUserInfo().split(":", 2);
            HikariDataSource source = new HikariDataSource();
            source.setDriverClassName("org.postgresql.Driver");
            source.setJdbcUrl("jdbc:postgresql://" + uri.getHost() + ":" + (uri.getPort() == -1 ? 5432 : uri.getPort()) + uri.getRawPath() + (uri.getRawQuery() == null ? "" : "?" + uri.getRawQuery()));
            source.setUsername(URLDecoder.decode(credentials[0], StandardCharsets.UTF_8));
            source.setPassword(credentials.length > 1 ? URLDecoder.decode(credentials[1], StandardCharsets.UTF_8) : "");
            return source;
        }
        return DataSourceBuilder.create().url(dbUrl).driverClassName(driver).username(username).password(password).build();
    }
}
