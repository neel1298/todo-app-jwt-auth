package com.example.jwtAuth.demo.Todo.TodoModel;

import java.util.Calendar;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="todo_info")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoModel {
	
	@Id 
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column 
	private String name;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy/MM/dd")
	@Column
	private Calendar due_date;


}
