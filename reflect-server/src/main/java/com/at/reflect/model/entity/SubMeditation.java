package com.at.reflect.model.entity;

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
@Table(name = "rft_submeditation")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class SubMeditation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "parent_meditation_id")
	private Integer parentMeditationId;
	@Column(name = "name")
	private String name;
	@Column(name = "meditation_player_address")
	private String meditationPlayerAdress;
	@Column(name = "meditation_audio_address")
	private String meditationAudioadress;
}
