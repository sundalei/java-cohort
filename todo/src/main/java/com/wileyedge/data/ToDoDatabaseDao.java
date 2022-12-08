package com.wileyedge.data;

import com.wileyedge.models.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@Profile("database")
public class ToDoDatabaseDao implements ToDoDao {

    private final JdbcTemplate jdbcTemplate;

    public ToDoDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo add(ToDo todo) {

        final String sql = "INSERT INTO todo(todo, note) VALUES (?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement =
                    connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, todo.getTodo());
            statement.setString(2, todo.getNote());
            return statement;
        }, keyHolder);

        todo.setId(keyHolder.getKey().intValue());

        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        final String sql = "SELECT id, todo, note, finished FROM todo";
        return jdbcTemplate.query(sql, new ToDoMapper());
    }

    @Override
    public ToDo findById(int id) {

        final String sql = "SELECT id, todo, note, finished "
                + "FROM todo WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new ToDoMapper(), id);
    }

    @Override
    public boolean update(ToDo toDo) {

        final String sql = "UPDATE todo SET "
                + "todo = ?, "
                + "note = ?, "
                + "finished = ? "
                + "WHERE id = ?";

        int rowAffected = jdbcTemplate.update(sql,
                toDo.getTodo(),
                toDo.getNote(),
                toDo.isFinished(),
                toDo.getId());
        return rowAffected > 0;
    }

    @Override
    public boolean deleteById(int id) {

        final String sql = "DELETE FROM todo WHERE id = ?";
        int rowAffected = jdbcTemplate.update(sql, id);
        return rowAffected > 0;
    }

    private static final class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ToDo toDo = new ToDo();
            toDo.setId(rs.getInt("id"));
            toDo.setTodo(rs.getString("todo"));
            toDo.setNote(rs.getString("note"));
            toDo.setFinished(rs.getBoolean("finished"));
            return toDo;
        }
    }
}
