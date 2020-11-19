package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.buildingriskassessments.BuildingRiskAssessments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRiskAssessmentsRepository extends MongoRepository<BuildingRiskAssessments, String> {
}
