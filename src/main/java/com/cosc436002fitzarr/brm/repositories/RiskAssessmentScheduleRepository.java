package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskAssessmentScheduleRepository extends MongoRepository<RiskAssessmentSchedule, String> {
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByBuildingRiskAssessmentId(String buildingRiskAssessmentId);
}
