package com.spring.hasdocTime.service;

import com.spring.hasdocTime.interfc.ChronicIllnessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChronicIllnessServiceImpl {

    @Autowired
    private ChronicIllnessInterface chronicIllnessDao;
}
