package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.user.SiteMaintenanceAssociate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteMaintenanceAssociateRepository extends MongoRepository<SiteMaintenanceAssociate, String> {

}
