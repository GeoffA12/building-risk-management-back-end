package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteMaintenanceManagerRepository extends MongoRepository<SiteMaintenanceManager, String> {
    @Query(value = "{ 'associatedSiteIds': { $in: ?0 } }")
    List<SiteMaintenanceManager> getSiteMaintenanceManagersBySite(List<String> associatedSiteIds);
}
