package com.example.jwtAuth.demo.TodoController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwtAuth.demo.Todo.TodoModel.TodoModel;


@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	public TodoModel SaveTodo(TodoModel todo) {
		return todoRepository.save(todo);
		
	}
	
	public List<TodoModel> getTodoList(){
		return todoRepository.findAll();
	}

	public TodoModel getTodo(UUID id) {
		
		return todoRepository.findById(id).orElse(null);
	}
	
	
}