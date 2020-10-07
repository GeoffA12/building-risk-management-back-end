package com.cosc436002fitzarr.brm.repositories;

import com.cosc436002fitzarr.brm.models.site.Site;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends MongoRepository<Site, String> {
    public Site getById(String id);
    public List<Site> findBySiteName(String siteName);
    public List<Site> findBySiteCode(String siteCode);
    @Query(value = "{  '_id': { $in: ?0 }}")
    public List<Site> findSitesById(List<String> ids);
}
