package com.spring.hasdocTime.service;

import com.spring.hasdocTime.repository.SymptomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SymptomServiceImpl {

    @Autowired
    private SymptomRepository symptomRepository;
}
