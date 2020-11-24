package com.cosc436002fitzarr.brm.models.sitemaintenancemanager;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.user.User;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "sitemaintenancemanager")
public class SiteMaintenanceManager extends User implements Entity {
    private List<String> siteMaintenanceAssociateIds;
    private List<String> buildingRiskAssessmentIdsFiled;
    private List<String> buildingIdsFiled;

    public SiteMaintenanceManager(String id, LocalDateTime updatedAt, LocalDateTime createdAt, List<EntityTrail> entityTrail, String publisherId, SiteRole siteRole,
                                  String firstName, String lastName, String username, String email, String phone, String authToken, String hashPassword,
                                  List<String> associatedSiteIds, List<String> siteMaintenanceAssociateIds, List<String> buildingRiskAssessmentIdsFiled,
                                  List<String> buildingIdsFiled) {
        super(id, updatedAt, createdAt, entityTrail, publisherId, siteRole, firstName, lastName, username, email, phone, authToken, hashPassword, associatedSiteIds);
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
        this.buildingRiskAssessmentIdsFiled = buildingRiskAssessmentIdsFiled;
        this.buildingIdsFiled = buildingIdsFiled;
    }

    public List<String> getSiteMaintenanceAssociateIds() {
        return siteMaintenanceAssociateIds;
    }

    public void setSiteMaintenanceAssociateIds(List<String> siteMaintenanceAssociateIds) {
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    public List<String> getBuildingRiskAssessmentIdsFiled() {
        return buildingRiskAssessmentIdsFiled;
    }

    public void setBuildingRiskAssessmentIdsFiled(List<String> buildingRiskAssessmentIdsFiled) {
        this.buildingRiskAssessmentIdsFiled = buildingRiskAssessmentIdsFiled;
    }

    public List<String> getBuildingIdsFiled() {
        return buildingIdsFiled;
    }

    public void setBuildingIdsFiled(List<String> buildingIdsFiled) {
        this.buildingIdsFiled = buildingIdsFiled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SiteMaintenanceManager that = (SiteMaintenanceManager) o;
        return getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds()) &&
                getBuildingRiskAssessmentIdsFiled().equals(that.getBuildingRiskAssessmentIdsFiled()) &&
                getBuildingIdsFiled().equals(that.getBuildingIdsFiled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSiteMaintenanceAssociateIds(), getBuildingRiskAssessmentIdsFiled(), getBuildingIdsFiled());
    }

    @Override
    public String toString() {
        return "SiteMaintenanceManager{" +
                "siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                ", buildingRiskAssessmentIdsFiled=" + buildingRiskAssessmentIdsFiled +
                ", buildingIdsFiled=" + buildingIdsFiled +
                '}';
    }
}
