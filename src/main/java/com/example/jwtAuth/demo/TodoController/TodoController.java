package com.example.jwtAuth.demo.TodoController;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtAuth.demo.Todo.TodoModel.TodoModel;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value="/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoservice;
	
	@PostMapping("/add")
	public void addTodo(@RequestBody TodoModel todo) {
		
		todoservice.SaveTodo(todo);	
		
	}
	
	@GetMapping("/getAllTodos")
	public List<TodoModel> getTodoList() {
		
		return todoservice.getTodoList();
		
	}
	
	@GetMapping("/getTodo/{todo_id}")
	public TodoModel getSingleTodo(@PathVariable UUID todo_id) {
		
		return todoservice.getTodo(todo_id);
	}
	
}