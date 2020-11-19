package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.building.Building;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingRepository extends MongoRepository<Building, String> {
}
