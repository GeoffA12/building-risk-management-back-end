package com.cosc436002fitzarr.brm.models.user;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "workplacehealthsafetymember")
public class WorkplaceHealthSafetyMember extends User implements Entity {
    private List<String> riskAssessmentsFiledIds;

    public WorkplaceHealthSafetyMember(String id, LocalDateTime updatedAt, LocalDateTime createdAt, List<EntityTrail> entityTrail,
                                       String publisherId, SiteRole siteRole, String firstName, String lastName, String username, String email, String phone,
                                       String authToken, String hashPassword, List<String> associatedSiteIds, List<String> riskAssessmentsFiledIds) {
        super(id, updatedAt, createdAt, entityTrail, publisherId, siteRole, firstName, lastName, username, email, phone, authToken, hashPassword, associatedSiteIds);
        this.riskAssessmentsFiledIds = riskAssessmentsFiledIds;
    }

    public List<String> getRiskAssessmentsFiledIds() {
        return riskAssessmentsFiledIds;
    }

    public void setRiskAssessmentsFiledIds(List<String> riskAssessmentsFiledIds) {
        this.riskAssessmentsFiledIds = riskAssessmentsFiledIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        WorkplaceHealthSafetyMember that = (WorkplaceHealthSafetyMember) o;
        return getRiskAssessmentsFiledIds().equals(that.getRiskAssessmentsFiledIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRiskAssessmentsFiledIds());
    }

    @Override
    public String toString() {
        return "WorkplaceHealthSafetyMember{" +
                "riskAssessmentsFiledIds=" + riskAssessmentsFiledIds +
                '}';
    }
}
