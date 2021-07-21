package com.example.jwtAuth.demo.TodoController;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jwtAuth.demo.Todo.TodoModel.TodoModel;


@Repository
public interface TodoRepository  extends JpaRepository<TodoModel, UUID>{


}
