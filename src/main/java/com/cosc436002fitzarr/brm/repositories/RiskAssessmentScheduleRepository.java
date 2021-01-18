package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.riskassessmentschedule.RiskAssessmentSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskAssessmentScheduleRepository extends MongoRepository<RiskAssessmentSchedule, String> {
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByBuildingRiskAssessmentId(String buildingRiskAssessmentId);

    @Query(value = "{ 'riskAssessmentId': { $in: ?0 }}")
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByRiskAssessmentIdList(List<String> riskAssessmentIdsOfBuildingRiskAssessment);

    @Query(value = "{ 'id': { $in: ?0 }}")
    public List<RiskAssessmentSchedule> getRiskAssessmentSchedulesByRiskAssessmentSchedulesIdsList(List<String> assignedRiskAssessmentScheduleIds);
}
