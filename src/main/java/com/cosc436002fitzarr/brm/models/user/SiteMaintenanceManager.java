package com.cosc436002fitzarr.brm.models.user;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document(collection = "sitemaintenancemanager")
public class SiteMaintenanceManager extends User implements Entity {
    private List<String> siteMaintenanceAssociateIds;

    public SiteMaintenanceManager(String id, LocalDateTime updatedAt, LocalDateTime createdAt, List<EntityTrail> entityTrail, String publisherId, SiteRole siteRole,
                                  String firstName, String lastName, String username, String email, String phone, String authToken, String hashPassword,
                                  List<String> associatedSiteIds, List<String> siteMaintenanceAssociateIds) {
        super(id, updatedAt, createdAt, entityTrail, publisherId, siteRole, firstName, lastName, username, email, phone, authToken, hashPassword, associatedSiteIds);
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    public List<String> getSiteMaintenanceAssociateIds() {
        return siteMaintenanceAssociateIds;
    }

    public void setSiteMaintenanceAssociateIds(List<String> siteMaintenanceAssociateIds) {
        this.siteMaintenanceAssociateIds = siteMaintenanceAssociateIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SiteMaintenanceManager that = (SiteMaintenanceManager) o;
        return getSiteMaintenanceAssociateIds().equals(that.getSiteMaintenanceAssociateIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSiteMaintenanceAssociateIds());
    }

    @Override
    public String toString() {
        return "SiteMaintenanceManager{" +
                "siteMaintenanceAssociateIds=" + siteMaintenanceAssociateIds +
                '}';
    }
}
