package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.site.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiskAssessmentRepository extends MongoRepository<RiskAssessment, String> {
    @Query(value = "{  '_id': { $in: ?0 }}")
    public List<RiskAssessment> findRiskAssessmentsById(List<String> ids);
}
