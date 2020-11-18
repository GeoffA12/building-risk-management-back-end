package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.buildingriskassessment.BuildingRiskAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRiskAssessmentRepository extends MongoRepository<BuildingRiskAssessment, String> {
}
