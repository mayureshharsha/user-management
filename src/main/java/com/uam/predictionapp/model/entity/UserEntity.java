package com.uam.predictionapp.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity()
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(
		name="User",
		uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})}
)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;
}