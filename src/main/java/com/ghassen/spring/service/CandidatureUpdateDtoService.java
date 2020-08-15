package com.ghassen.spring.service;

import com.ghassen.spring.domain.Candidature;
import com.ghassen.spring.web.dto.CandidatureUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keno&Kemo on 03.12.2017..
 */

@Service
public class CandidatureUpdateDtoService {

    private CandidatureService candidatureService;
    private ModelMapper modelMapper;

    public CandidatureUpdateDtoService(CandidatureService candidatureService, ModelMapper modelMapper) {
        this.candidatureService = candidatureService;
        this.modelMapper = modelMapper;
    }

   
    public List<CandidatureUpdateDto> findAll(){
        List<Candidature> candidatureList = candidatureService.findAllEagerly();
        List<CandidatureUpdateDto> candidatureUpdateDtosList = new ArrayList<>();

        for(Candidature candidature : candidatureList){
            candidatureUpdateDtosList.add(modelMapper.map(candidature, CandidatureUpdateDto.class));
        }
        return candidatureUpdateDtosList;
    }

    public CandidatureUpdateDto findById(Long id){
        return modelMapper.map(candidatureService.findByIdEagerly(id), CandidatureUpdateDto.class);
    }


}
