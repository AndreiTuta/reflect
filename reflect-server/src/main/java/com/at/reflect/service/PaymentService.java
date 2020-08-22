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
public class PaymentService implements Service {

   
    @Override
    public ServiceType getType() {
        return ServiceType.PAYMENT;
    }

    public Object delete(String paymentId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object fetchPaymentById(String paymentId) {
        // TODO Auto-generated method stub
        return null;
    }

}
