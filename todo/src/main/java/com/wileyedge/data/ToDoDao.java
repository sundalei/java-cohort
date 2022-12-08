package com.wileyedge.data;

import com.wileyedge.models.ToDo;

import java.util.List;

public interface ToDoDao {

    ToDo add(ToDo todo);

    List<ToDo> getAll();

    ToDo findById(int id);

    /**
     * Update todo.
     * @param toDo
     * @return True if item exists and is updated
     */
    boolean update(ToDo toDo);

    /**
     * Delete by id.
     * @param id
     * @return True if item exists and is deleted
     */
    boolean deleteById(int id);
}
