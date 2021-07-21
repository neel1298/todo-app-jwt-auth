package com.example.jwtAuth.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name="user_role")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
	
}
