package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.SiteMaintenanceAssociate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteMaintenanceAssociateRepository extends MongoRepository<SiteMaintenanceAssociate, String> {
    @Query(value = "{ 'associatedSiteIds': { $in: ?0 } }")
    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySite(List<String> associatedSiteIds);

    @Query(value = "{ '_id': { $in: ?0 } }")
    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySiteMaintenanceAssociateIdList(List<String> siteMaintenanceAssociateIds);

    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySiteMaintenanceManagerId(String siteMaintenanceManagerId);
}
