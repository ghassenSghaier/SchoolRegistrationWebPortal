package com.ghassen.spring.service.searching;


import com.ghassen.spring.web.dto.CandidatureDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatureSearchResult {
    private Page<CandidatureDto> candidaturePage;
    private boolean numberFormatException;
}
