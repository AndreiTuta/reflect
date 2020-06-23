package com.at.reflect.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author at
 */
@Entity
@Table(name = "rft_meditation")
@Data
@Getter
@NoArgsConstructor
@Setter
public class Meditation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "available")
	private boolean available;
	@Column(name = "name")
	private String name;
	@Column(name = "duration")
	private String duration;
	@Column(name = "preview")
	private String preview;
	@Column(name = "address")
	private String address;
	@Column(name = "numMed")
	private Integer numMed;
	@Transient
	private List<SubMeditation> submeditations;
}