package com.at.reflect.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.MeditationDao;
import com.at.reflect.dao.SubmeditationDao;
import com.at.reflect.dao.UserMeditationDao;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.request.MeditationRequest;
import com.at.reflect.model.request.UserMeditationRequest;
import com.at.reflect.model.response.MeditationResponse;
import com.at.reflect.model.response.SubmeditationResponse;
import com.at.reflect.model.response.UserMeditationResponse;
import com.reflect.generated.tables.pojos.Meditation;
import com.reflect.generated.tables.pojos.Submeditation;
import com.reflect.generated.tables.pojos.UserMeditation;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeditationService implements Service {

    private final MeditationDao meditationDao;
    private final SubmeditationDao submeditationDao;
    private final UserMeditationDao userMeditationDao;
    private final ModelMapper modelMapper;

    public MeditationResponse createMeditation(MeditationRequest meditationRequest) {
        final Meditation meditation = modelMapper.map(meditationRequest, Meditation.class);
        final List<Submeditation> submeditations = meditationRequest.getSubmeditations()
                                                                    .stream()
                                                                    .map(submeditation -> modelMapper.map(submeditation,
                                                                                                          Submeditation.class))
                                                                    .collect(Collectors.toList());
        meditationDao.insert(meditation);
        saveSubmeditations(submeditations, Optional.of(meditation.getId()));
        return buildMeditationResponse(meditation).build();
    }

    public UserMeditationResponse createUserMeditation(UserMeditationRequest userMeditationRequest) {
        final UserMeditation userMeditation = modelMapper.map(userMeditationRequest, UserMeditation.class);
        userMeditationDao.insertUserMeditation(userMeditation);
        return buildUserMeditationResponse(userMeditation).build();
    }

    public MeditationResponse fetchMeditationById(String meditationId) throws NotFoundException {
        try {
            int id = Integer.parseInt(meditationId);
            final Meditation meditation = fetchMeditationById(id)
                                                                 .orElseThrow(() -> new NotFoundException("Meditation with id: "
                                                                     + meditationId + " not found"));
            List<SubmeditationResponse> submeditations = submeditationDao.fetchByParentMeditationId(id)
                                                                         .stream()
                                                                         .map(this::convert)
                                                                         .collect(Collectors.toList());
            return buildMeditationResponse(meditation).submeditations(submeditations).build();
        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    public UserMeditationResponse fetchUserMeditationById(String userMeditationId) throws NotFoundException {
        try {
            int id = Integer.parseInt(userMeditationId);
            final UserMeditation userMeditation = fetchUserMeditationById(id)
                                                                             .orElseThrow(() -> new NotFoundException("User meditation with id: "
                                                                                 + userMeditationId + " not found"));
            return buildUserMeditationResponse(userMeditation).build();
        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    private SubmeditationResponse convert(Submeditation submeditation) {
        return modelMapper.map(submeditation, SubmeditationResponse.class);
    }

    public MeditationResponse updateMeditation(String meditationId, MeditationRequest meditationRequest) throws NotFoundException {
        try {
            int id = Integer.parseInt(meditationId);
            fetchMeditationById(id)
                                   .orElseThrow(() -> new NotFoundException("Meditation with id: " + meditationId + " not found"));

            final Meditation meditation = modelMapper.map(meditationRequest, Meditation.class);
            meditation.setId(id);
            updateMeditation(meditation);
            return buildMeditationResponse(meditation).build();
        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    public Optional<Meditation> fetchMeditationById(final Integer id) {
        return Optional.ofNullable(meditationDao.findById(id));
    }

    public Optional<UserMeditation> fetchUserMeditationById(final Integer id) {
//        TODO: Find another way for this to be handled
        return Optional.ofNullable(userMeditationDao.fetchByMeditationId(id).get(0));
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
            submeditations.forEach(sm -> {
                sm.setParentMeditationId(id);
            });
            saveSubMeditations(submeditations);
        });
    }

    public void saveSubMeditations(final List<Submeditation> submeditations) {
        submeditationDao.save(submeditations);
    }

    private MeditationResponse.MeditationResponseBuilder buildMeditationResponse(final Meditation meditation) {
        return MeditationResponse.builder()
                                 .id(meditation.getId())
                                 .name(meditation.getName())
                                 .duration(meditation.getDuration())
                                 .address(meditation.getAddress())
                                 .numMed(meditation.getNumMed())
                                 .preview(meditation.getPreview())
                                 .isAvailable(meditation.getAvailable());
    }

    private UserMeditationResponse.UserMeditationResponseBuilder buildUserMeditationResponse(UserMeditation userMeditation) {
        return UserMeditationResponse.builder()
                                     .id(userMeditation.getUserId())
                                     .meditationId(userMeditation.getMeditationId())
                                     .userId(userMeditation.getUserId())
                                     .userMeditationText(userMeditation.getUserMeditationText());
    }

    @Override
    public ServiceType getType() {
        return ServiceType.MED;
    }

    public Object updateUserMeditation(String userMeditationId, @Valid UserMeditationRequest meditationRequest) {
        // TODO Auto-generated method stub
        return null;
    }
}
