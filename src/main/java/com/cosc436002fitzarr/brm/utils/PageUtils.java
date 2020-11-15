package com.cosc436002fitzarr.brm.utils;

import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.user.User;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageUtils {
    private static final String userDataKey = "users";
    private static final String riskAssessmentDataKey = "riskassessments";
    private static final String totalPagesKey = "totalPages";
    private static final String totalElementsKey = "totalElements";
    private static final String currentPageNumberKey = "currentPage";

    public static Map<String, Object> getUserMappingResponse(Page<User> objectPages, List<User> filteredUserContent) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(userDataKey, filteredUserContent);
        responseMap.put(totalPagesKey, objectPages.getTotalPages());
        responseMap.put(totalElementsKey, filteredUserContent.size());
        responseMap.put(currentPageNumberKey, objectPages.getNumber());
        return responseMap;
    }

    public static Map<String, Object> getRiskAssessmentMappingResponse(Page<RiskAssessment> riskAssessmentsPage, List<RiskAssessment> filteredRiskAssessmentContent) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put(riskAssessmentDataKey, filteredRiskAssessmentContent);
        responseMap.put(totalPagesKey, riskAssessmentsPage.getTotalPages());
        responseMap.put(totalElementsKey, filteredRiskAssessmentContent.size());
        responseMap.put(currentPageNumberKey, riskAssessmentsPage.getNumber());
        return responseMap;
    }
}
