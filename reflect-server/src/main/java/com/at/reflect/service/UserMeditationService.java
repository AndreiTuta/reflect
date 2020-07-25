package com.at.reflect.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.MeditationDaoImplementation;
import com.at.reflect.dao.SubmeditationDaoImplementation;
import com.at.reflect.dao.UserDaoImplementation;
import com.at.reflect.dao.UserMeditationDaoImplementation;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.request.MeditationRequest;
import com.at.reflect.model.response.MeditationResponse;
import com.at.reflect.model.response.SubmeditationResponse;
import com.reflect.generated.tables.pojos.Meditation;
import com.reflect.generated.tables.pojos.Submeditation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMeditationService implements Service {

	private final UserDaoImplementation userDao;
	private final UserMeditationDaoImplementation userMeditationDao;
	private final MeditationDaoImplementation meditationDao;
	private final SubmeditationDaoImplementation submeditationDao;
	private final ModelMapper modelMapper;

	public MeditationResponse createMeditation(MeditationRequest meditationRequest) {
		final Meditation meditation = modelMapper.map(meditationRequest, Meditation.class);
		meditationDao.insert(meditation);
		return buildMeditationResponse(meditation).build();
	}

	public MeditationResponse fetchMeditationById(String meditationId) throws NotFoundException {
		try {
			int id = Integer.parseInt(meditationId);
			final Meditation meditation = fetchMeditationById(id)
					.orElseThrow(() -> new NotFoundException("Meditation with id: " + meditationId + " not found"));
			List<SubmeditationResponse> submeditations = submeditationDao.fetchByParentMeditationId(id).stream()
					.map(this::convert).collect(Collectors.toList());
			return buildMeditationResponse(meditation).submeditations(submeditations).build();
		} catch (NumberFormatException e) {
			throw new PathException("meditationId on path must be an integer");
		}
	}

	private SubmeditationResponse convert(Submeditation submeditation) {
		return modelMapper.map(submeditation, SubmeditationResponse.class);
	}

	public void updateMeditation(String meditationId, MeditationRequest meditationRequest) throws NotFoundException {
		try {
			int id = Integer.parseInt(meditationId);
			fetchMeditationById(id)
					.orElseThrow(() -> new NotFoundException("Meditation with id: " + meditationId + " not found"));

			final Meditation meditation = modelMapper.map(meditationRequest, Meditation.class);
			meditation.setId(id);
			updateMeditation(meditation);
		} catch (NumberFormatException e) {
			throw new PathException("meditationId on path must be an integer");
		}
	}

	public Optional<Meditation> fetchMeditationById(final Integer id) {
		return Optional.ofNullable(meditationDao.findById(id));
	}

	public List<Meditation> fetchMeditationByName(final String meditationName) {
		return meditationDao.fetchByName(meditationName);
	}

	public void updateMeditation(final Meditation meditation) {
		meditationDao.update(meditation);
	}

	public Map<Integer, List<Submeditation>> fetchSubmeditations(final List<Meditation> meditations) {
		return submeditationDao.fetchByMedId(meditations.stream().map(m -> m.getId()).collect(Collectors.toList()));
	}

	public Map<Integer, List<Submeditation>> fetchSubmeditationsByMedId(final List<Integer> meditationsId) {
		return submeditationDao.fetchByMedId(meditationsId);
	}

	public void saveSubmeditations(final List<Submeditation> submeditations, final Optional<Integer> medId) {
		medId.ifPresent(id -> {
			submeditations.forEach(sm -> sm.setParentMeditationId(id));
			saveSubMeditations(submeditations);
		});
	}

	public void saveSubMeditations(final List<Submeditation> submeditations) {
		submeditationDao.save(submeditations);
	}

	private MeditationResponse.MeditationResponseBuilder buildMeditationResponse(final Meditation meditation) {
		return MeditationResponse.builder().id(meditation.getId()).name(meditation.getName())
				.duration(meditation.getDuration()).address(meditation.getAddress()).numMed(meditation.getNumMed())
				.preview(meditation.getPreview()).available(meditation.getAvailable());
	}

	@Override
	public ServiceType getType() {
		return ServiceType.MED;
	}

}
