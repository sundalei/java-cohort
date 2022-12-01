package com.wileyedge.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import com.wileyedge.dao.EmployeeDao;
import com.wileyedge.entity.Employee;

@Repository
public class EmployeeDaoDB implements EmployeeDao {
    
    private final JdbcTemplate jdbcTemplate;

    public EmployeeDaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final class EmployeeMapper implements RowMapper<Employee> {
        @Override
        @Nullable
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("firstName"));
            employee.setLastName(rs.getString("lastName"));
            return employee;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employee";
        return jdbcTemplate.query(SELECT_ALL_EMPLOYEES, new EmployeeMapper());
    }
}
