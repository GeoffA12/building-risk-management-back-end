package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkplaceHealthSafetyMemberRepository extends MongoRepository<WorkplaceHealthSafetyMember, String> {
    @Query(value = "{ 'associatedSiteIds': { $in: ?0 } }")
    public List<WorkplaceHealthSafetyMember> getWorkplaceHealthSafetyMembersBySite(List<String> associatedSiteIds);
}
