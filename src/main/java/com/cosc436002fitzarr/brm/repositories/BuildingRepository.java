package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.building.Building;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends MongoRepository<Building, String> {
    @Query(value = "{ 'siteId': { $in: ?0 }}")
    public List<Building> findBuildingsByAssociatedSiteIds(List<String> associatedSiteIds);
}
