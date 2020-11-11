package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceHealthSafetyMemberRepository extends MongoRepository<WorkplaceHealthSafetyMember, String> {
}
