package com.ghassen.spring.service.searching;

import com.ghassen.spring.service.CandidatureDtoService;
import com.ghassen.spring.service.UserDtoService;
import com.ghassen.spring.web.dto.CandidatureDto;
import com.ghassen.spring.web.dto.UserDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Data
@Service
public class CandidatureFinder {
    private CandidatureDtoService candidatureDtoService;

    @Autowired
    public CandidatureFinder(CandidatureDtoService candidatureDtoService) {
        this.candidatureDtoService = candidatureDtoService;
    }

    public CandidatureSearchResult searchCandidaturesByProperty(PageRequest pageRequest, CandidatureSearchParameters candidatureSearchParameters) {
        Page<CandidatureDto> candidatureDtoPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);
        switch (candidatureSearchParameters.getCandidaturesProperty().get()) {
            case "ID":
                try {
                    long id = Long.parseLong(candidatureSearchParameters.getPropertyValue().get());
                    candidatureDtoPage = candidatureDtoService.findByIdPageable(id, pageRequest);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return new CandidatureSearchResult(candidatureDtoService.findAllPageable(pageRequest), true);
                }
                break;
            case "Name":
                candidatureDtoPage = candidatureDtoService.findByNameContaining(candidatureSearchParameters.getPropertyValue().get(), pageRequest);
                break;
            case "Surname":
                candidatureDtoPage = candidatureDtoService.findBySurnameContaining(candidatureSearchParameters.getPropertyValue().get(), pageRequest);
                break;           
            case "Email":
                candidatureDtoPage = candidatureDtoService.findByEmailContaining(candidatureSearchParameters.getPropertyValue().get(), pageRequest);
                break;
        }
        return new CandidatureSearchResult(candidatureDtoPage, false);
    }
}
