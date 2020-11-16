package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteMaintenanceManagerRepository extends MongoRepository<SiteMaintenanceManager, String> {
}
