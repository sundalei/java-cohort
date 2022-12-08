package com.wileyedge.controllers;

import com.wileyedge.data.ToDoDao;
import com.wileyedge.models.ToDo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class ToDoController {

    private final ToDoDao toDoDao;

    public ToDoController(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    @GetMapping
    public List<ToDo> all() {
        return toDoDao.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo create(@RequestBody ToDo toDo) {
        return toDoDao.add(toDo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> findById(@PathVariable int id) {
        ToDo result = toDoDao.findById(id);
        if (result == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody ToDo toDo) {

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        if (id != toDo.getId()) {
            responseEntity = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (toDoDao.update(toDo)) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        if (toDoDao.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
