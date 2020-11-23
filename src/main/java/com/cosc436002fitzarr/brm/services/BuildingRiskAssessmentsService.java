package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.repositories.BuildingRiskAssessmentsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BuildingRiskAssessmentsService {
    @Autowired
    public BuildingRiskAssessmentsRepository buildingRiskAssessmentsRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(BuildingRiskAssessmentsService.class);

}
