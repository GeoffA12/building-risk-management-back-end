package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.user.SiteMaintenanceManager;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteMaintenanceManagerRepository extends MongoRepository<SiteMaintenanceManager, String> {
}
