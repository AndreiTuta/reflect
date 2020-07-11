package com.at.reflect.model.entity.meditation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
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
@Table(name = "rft_user_meditation")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class UserMeditation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@PrimaryKeyJoinColumn
	@Column(name = "user_id")
	private Integer userId;
	@PrimaryKeyJoinColumn
	@Column(name = "meditation_id")
	private Integer meditationId;
	@Column(name = "user_meditation_text")
	private String userMeditationTextString;

}
