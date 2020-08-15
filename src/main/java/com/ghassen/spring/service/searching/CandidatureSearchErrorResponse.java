package com.ghassen.spring.service.searching;

import com.ghassen.spring.service.CandidatureDtoService;
import com.ghassen.spring.web.paging.InitialPagingSizes;
import com.ghassen.spring.web.paging.Pager;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class CandidatureSearchErrorResponse {

    private CandidatureDtoService candidatureDtoService;

    public CandidatureSearchErrorResponse(CandidatureDtoService candidatureDtoService) {
        this.candidatureDtoService = candidatureDtoService;
    }

    public ModelAndView respondToNumberFormatException(CandidatureSearchResult candidatureSearchResult, ModelAndView modelAndView) {
        Pager pager = new Pager(candidatureSearchResult.getCandidaturePage().getTotalPages(), candidatureSearchResult.getCandidaturePage().getNumber(),
                                InitialPagingSizes.BUTTONS_TO_SHOW, candidatureSearchResult.getCandidaturePage().getTotalElements());

        modelAndView.addObject("numberFormatException", true);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("candidatures", candidatureSearchResult.getCandidaturePage());
        modelAndView.setViewName("adminPage/candidature/candidatures");
        return modelAndView;
    }

    public ModelAndView respondToEmptySearchResult(ModelAndView modelAndView, PageRequest pageRequest) {
        modelAndView.addObject("noMatches", true);
        modelAndView.addObject("candidatures", candidatureDtoService.findAllPageable(pageRequest));
        return modelAndView;
    }
}
