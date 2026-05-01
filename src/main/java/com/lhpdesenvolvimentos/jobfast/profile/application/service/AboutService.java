package com.lhpdesenvolvimentos.jobfast.profile.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.job.domain.exception.DomainException;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutResponse;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AboutEntity;
import com.lhpdesenvolvimentos.jobfast.profile.domain.repository.AboutRepository;
import com.lhpdesenvolvimentos.jobfast.user.domain.model.UserEntity;
import com.lhpdesenvolvimentos.jobfast.user.domain.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AboutService {
    private final AboutRepository aboutRepository;
    private final UserRepository userRepository;
    public AboutService(AboutRepository aboutRepository, UserRepository userRepository) {
        this.aboutRepository = aboutRepository;
        this.userRepository = userRepository;
    }
    
    public AboutResponse updateAbout(String userId, AboutRequest request) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new DomainException("User not found"));
       
         int genderNum = request.genderNum();
        AboutEntity.Gender gender = getGender(genderNum);
        AboutEntity about = AboutEntity.builder()
            .user(user)
            .zipCode(request.zipCode())
            .address(request.address())
            .cityName(request.cityName())
            .state(request.state())
            .disability(request.disability())
            .gender(gender)
            .build();
       
        aboutRepository.save(about);
        return new AboutResponse("About information updated successfully");
    }

    private AboutEntity.Gender getGender(int genderNum) throws RuntimeException {
       switch (genderNum) {
           case 0 -> {
               return AboutEntity.Gender.MALE;
            }
           case 1 -> {
               return AboutEntity.Gender.FEMALE;
            }
           case 2 -> {
               return AboutEntity.Gender.OTHER;
            }
           case 3 -> {
               return AboutEntity.Gender.PREFER_NOT_TO_SAY;
            }
           case 4 -> {
               return AboutEntity.Gender.NON_BINARY;
            }
           default -> throw new RuntimeException("Invalid gender provided");
       }
    }
}
