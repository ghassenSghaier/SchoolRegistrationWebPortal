package com.ghassen.spring.service;

import com.ghassen.spring.domain.Candidature;
import com.ghassen.spring.web.dto.CandidatureDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Keno&Kemo on 04.12.2017..
 */
@Service
public class CandidatureDtoService {

    private CandidatureService candidatureService;
    private ModelMapper modelMapper;

    public CandidatureDtoService(CandidatureService candidatureService, ModelMapper modelMapper) {
        this.candidatureService = candidatureService;
        this.modelMapper = modelMapper;
    }

    public List<CandidatureDto> findAll(){
        List<Candidature> candidatures = candidatureService.findAll();
        return candidatures.stream().map(candidature -> modelMapper.map(candidature, CandidatureDto.class)).collect(Collectors.toList());
    }

    public Page<CandidatureDto> findAllPageable(Pageable pageable) {
        Page<Candidature> candidatures = candidatureService.findAllPageable(pageable);
        List<CandidatureDto> candidatureDtos = candidatures.stream().map(user -> modelMapper.map(user, CandidatureDto.class)).collect(Collectors.toList());
        return new PageImpl<>(candidatureDtos, pageable, candidatures.getTotalElements());
    }

    public Optional<CandidatureDto> findById(Long id) {
        Optional<Candidature> retrievedCandidature = candidatureService.findById(id);
        return retrievedCandidature.map(user -> modelMapper.map(user, CandidatureDto.class));
    }

    public CandidatureDto findByEmail(String email){
        return modelMapper.map(candidatureService.findByEmail(email), CandidatureDto.class);
    }

    public Page<CandidatureDto> findByIdPageable(Long id, PageRequest pageRequest) {
        Page<Candidature> users = candidatureService.findByIdPageable(id, pageRequest);
        List<CandidatureDto> userDtos = users.stream().map(user -> modelMapper.map(user, CandidatureDto.class)).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageRequest, users.getTotalElements());
    }

    public Page<CandidatureDto> findByNameContaining(String name, PageRequest pageRequest) {
        Page<Candidature> candidatures = candidatureService.findByNameContaining(name, pageRequest);
        List<CandidatureDto> userDtos = candidatures.stream().map(user -> modelMapper.map(user, CandidatureDto.class)).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageRequest, candidatures.getTotalElements());
    }

    public Page<CandidatureDto> findBySurnameContaining(String surname, PageRequest pageRequest) {
        Page<Candidature> users = candidatureService.findBySurnameContaining(surname, pageRequest);
        List<CandidatureDto> userDtos = users.stream().map(user -> modelMapper.map(user, CandidatureDto.class)).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageRequest, users.getTotalElements());
    }
    
    public Page<CandidatureDto> findByEmailContaining(String email, PageRequest pageRequest) {
        Page<Candidature> users = candidatureService.findByEmailContaining(email, pageRequest);
        List<CandidatureDto> userDtos = users.stream().map(user -> modelMapper.map(user, CandidatureDto.class)).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageRequest, users.getTotalElements());
    }
}
