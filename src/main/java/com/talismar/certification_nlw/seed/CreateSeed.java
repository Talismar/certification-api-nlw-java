package com.talismar.certification_nlw.seed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class CreateSeed {
    private final JdbcTemplate jdbcTemplate;
    
    public CreateSeed(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/certification_nlw");
        dataSource.setUsername("docker");
        dataSource.setPassword("docker");

        CreateSeed createSedd = new CreateSeed(dataSource);
        createSedd.run(args);
    }

    public void run(String ...args) {
        this.executeSqlFile("src/main/resources/create.sql");
    }

    private void executeSqlFile(String filePath) {
        try {
            String sqlString = new String(Files.readAllBytes(Paths.get(filePath)));

            jdbcTemplate.execute(sqlString);
            System.out.println("Seed executed successfully");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
