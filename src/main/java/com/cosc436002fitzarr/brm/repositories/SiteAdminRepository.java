package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.user.SiteAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteAdminRepository extends MongoRepository<SiteAdmin, String> {

}
