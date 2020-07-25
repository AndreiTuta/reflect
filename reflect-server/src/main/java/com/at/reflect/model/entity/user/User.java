package com.at.reflect.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author at
 */
@Entity
@Table(name = "user")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "created")
	private String created;
	@Column(name = "email")
	private String email;
	@Column(name = "last_udpated")
	private String last_updated;
	@Column(name = "name")
	private String name;
	@Column(name = "password")
	private String password;

}