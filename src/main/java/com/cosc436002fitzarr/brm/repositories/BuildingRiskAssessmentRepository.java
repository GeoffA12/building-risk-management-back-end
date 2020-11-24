package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.buildingriskassessments.BuildingRiskAssessment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRiskAssessmentRepository extends MongoRepository<BuildingRiskAssessment, String> {
    @Query(value = "{ '_id': { $in: ?0 }}")
    List<BuildingRiskAssessment> getBuildingRiskAssessmentsBySite(List<String> bulkBuildingRiskAssessmentIdList);
}
