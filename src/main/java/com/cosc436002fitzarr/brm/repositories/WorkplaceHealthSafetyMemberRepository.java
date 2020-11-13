package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkplaceHealthSafetyMemberRepository extends MongoRepository<WorkplaceHealthSafetyMember, String> {
    @Query(value = "{ $and: [ { '_id': { $ne: ?0 } }, { 'associatedSiteIds': { $in: ?1 } } ] }")
    public List<WorkplaceHealthSafetyMember> getWorkplaceHealthSafetyMembersByUserIdAndSite(String userId, List<String> associatedSiteIds);
}
