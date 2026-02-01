package com.wellu.usermanagement.service;

import com.wellu.usermanagement.dto.request.InjuryRequestDto;
import com.wellu.usermanagement.dto.response.InjuryResponseDto;
import com.wellu.usermanagement.entity.Injury;
import com.wellu.usermanagement.enumeration.SeverityLevel;
import com.wellu.usermanagement.exception.ResourceNotFoundException;
import com.wellu.usermanagement.mapper.InjuryMapper;
import com.wellu.usermanagement.repository.InjuryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InjuryService {
    private final InjuryRepository injuryRepository;
    private final InjuryMapper injuryMapper;

    public InjuryResponseDto createInjury(InjuryRequestDto dto) {
        Injury injury = injuryMapper.toEntity(dto);
        Injury saved = injuryRepository.save(injury);
        return injuryMapper.toDto(saved);
    }
    public List<Injury> getByHealthProfile(UUID healthProfileID){
        return injuryRepository.findByHealthProfileId(healthProfileID);
    }
    public List<Injury> getAllInjuries(){
        return injuryRepository.findAll();
    }
    public Injury updateSeverity(UUID injuryId, SeverityLevel level){
        Injury injury = injuryRepository.findById(injuryId)
                .orElseThrow(() -> new ResourceNotFoundException("Injury not found"));
        injury.updateSeverityLevel(level);
        return injuryRepository.save(injury);
    }
}
