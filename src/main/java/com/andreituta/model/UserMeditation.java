/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.andreituta.model;

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
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "meditation_id")
	private Integer meditationId;
}
