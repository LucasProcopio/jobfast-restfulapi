package com.lhpdesenvolvimentos.jobfast.profile.application.service;

import org.springframework.stereotype.Service;

import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutRequest;
import com.lhpdesenvolvimentos.jobfast.profile.application.dto.AboutResponse;
import com.lhpdesenvolvimentos.jobfast.profile.domain.model.AboutEntity;
import com.lhpdesenvolvimentos.jobfast.profile.domain.repository.AboutRepository;

@Service
public class AboutService {
    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }
    
    public AboutResponse updateAbout(AboutRequest request) {
        // find user id by authentication bearer token, set to the builder to save it
        int genderNum = request.genderNum();
        AboutEntity.Gender gender = getGender(genderNum);
        AboutEntity about = AboutEntity.builder()
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
