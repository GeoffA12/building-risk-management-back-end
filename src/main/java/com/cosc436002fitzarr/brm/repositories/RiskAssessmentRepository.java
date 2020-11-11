package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskAssessmentRepository extends MongoRepository<RiskAssessment, String> {
}
