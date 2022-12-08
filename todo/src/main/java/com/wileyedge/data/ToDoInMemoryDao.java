package com.wileyedge.data;

import com.wileyedge.models.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("memory")
public class ToDoInMemoryDao implements ToDoDao {

    private static final List<ToDo> todos = new ArrayList<>();

    @Override
    public ToDo add(ToDo todo) {

        int nextId = todos.stream()
                .mapToInt(ToDo::getId)
                .max().orElse(0) + 1;

        todo.setId(nextId);
        todos.add(todo);
        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        return todos;
    }

    @Override
    public ToDo findById(int id) {
        return todos.stream()
                .filter(toDo -> toDo.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(ToDo toDo) {

        int index = 0;
        while (index < todos.size()
                && todos.get(index).getId() != toDo.getId()) {
            index++;
        }

        if (index < todos.size()) {
            todos.set(index, toDo);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(toDo -> toDo.getId() == id);
    }
}
