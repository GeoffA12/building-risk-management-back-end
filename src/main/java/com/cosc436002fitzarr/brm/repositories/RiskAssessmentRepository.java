package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskAssessmentRepository extends MongoRepository<RiskAssessment, String> {
    public RiskAssessment getById(String id);
}
