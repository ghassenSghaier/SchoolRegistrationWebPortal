package com.ghassen.spring.service.searching;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Component
public class CandidatureSearchParameters {
    private String candidaturesProperty;
    private String propertyValue;
    private Integer pageSize;
    private Integer page;

    public Optional<String> getCandidaturesProperty() {
        return Optional.ofNullable(candidaturesProperty);
    }

    public Optional<String> getPropertyValue() {
        return Optional.ofNullable(propertyValue);
    }

    public Optional<Integer> getPageSize() {
        return Optional.ofNullable(pageSize);
    }

    public Optional<Integer> getPage() {
        return Optional.ofNullable(page);
    }
}
