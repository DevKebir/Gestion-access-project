package com.episen.membership.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class RoleRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(RoleRepository.class);

    public void addAccess(String accessName) {
        try {
            // Check if the access already exists
            String checkAccessSql = "SELECT id FROM access WHERE name = ?";
            Integer accessId = jdbcTemplate.queryForObject(checkAccessSql, Integer.class, accessName);

            // If the access exists, do nothing
            if (accessId != null) {
                return;
            }
        } catch (EmptyResultDataAccessException e) {
            // Ignore the exception if no result is found
        }

        // Access doesn't exist, then add it
        String addAccessSql = "INSERT INTO access (name) VALUES (?)";
        jdbcTemplate.update(addAccessSql, accessName);
        logger.info("=====================================");
        logger.info("Access added: {}", accessName);
        logger.info("=====================================");
    }
}
