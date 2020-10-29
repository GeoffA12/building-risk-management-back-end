package com.cosc436002fitzarr.brm.models.user;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "sitemaintenanceassociate")
public class SiteMaintenanceAssociate extends User implements Entity {
    private String siteMaintenanceManagerId;
    private List<String> assignedBuildingRiskAssessmentIds;

    public SiteMaintenanceAssociate(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail, String publisherId,
    SiteRole siteRole, String firstName, String lastName, String username, String email, String phone, String authToken,
    String hashPassword, List<String> associatedSiteIds, String siteMaintenanceManagerId, List<String> assignedBuildingRiskAssessmentIds) {
        super(id, createdAt, updatedAt, entityTrail, publisherId, siteRole, firstName, lastName, username, email, phone, authToken, hashPassword, associatedSiteIds);
        this.siteMaintenanceManagerId = siteMaintenanceManagerId;
        this.assignedBuildingRiskAssessmentIds = assignedBuildingRiskAssessmentIds;
    }

    public String getSiteMaintenanceManagerId() {
        return siteMaintenanceManagerId;
    }

    public void setSiteMaintenanceManagerId(String siteMaintenanceManagerId) {
        this.siteMaintenanceManagerId = siteMaintenanceManagerId;
    }

    public List<String> getAssignedBuildingRiskAssessmentIds() {
        return assignedBuildingRiskAssessmentIds;
    }

    public void setAssignedBuildingRiskAssessments(List<String> assignedBuildingRiskAssessments) {
        this.assignedBuildingRiskAssessmentIds = assignedBuildingRiskAssessments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SiteMaintenanceAssociate that = (SiteMaintenanceAssociate) o;
        return getSiteMaintenanceManagerId().equals(that.getSiteMaintenanceManagerId()) &&
                getAssignedBuildingRiskAssessmentIds().equals(that.getAssignedBuildingRiskAssessmentIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSiteMaintenanceManagerId(), getAssignedBuildingRiskAssessmentIds());
    }

    @Override
    public String toString() {
        return "SiteMaintenanceAssociate{" +
                "siteMaintenanceManagerId='" + siteMaintenanceManagerId + '\'' +
                ", assignedBuildingRiskAssessments=" + assignedBuildingRiskAssessmentIds +
                '}';
    }
}
