package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.models.site.input.*;
import com.cosc436002fitzarr.brm.models.siteadmin.SiteAdmin;
import com.cosc436002fitzarr.brm.repositories.SiteAdminRepository;
import com.cosc436002fitzarr.brm.repositories.SiteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// TODO: Implement a '/removeUserIdFromUserIdList' that will be called when a user is deleted.
@Service
public class SiteService {
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SiteAdminRepository siteAdminRepository;
    @Autowired
    private SiteAdminService siteAdminService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

    // TODO: Use more helpful exception handling. I.E., should not be catching a generic exception everytime. Be more specific with error handling
    // TODO: Input validation on each API
    public Site createSite(CreateSiteInput input) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String id = UUID.randomUUID().toString();

        List<EntityTrail> entityList = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        List<SiteAdmin> allSiteAdmins = siteAdminRepository.findAll();
        for (SiteAdmin siteAdmin : allSiteAdmins) {
            userIds.add(siteAdmin.getId());
        }
        entityList.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedSiteSystemComment()));
        Site siteForPersistence = new Site(
            id,
            currentTime,
            currentTime,
            entityList,
            input.getPublisherId(),
            input.getSiteName(),
            input.getSiteCode(),
            input.getAddress(),
            userIds
        );

        try {
            siteRepository.save(siteForPersistence);
            LOGGER.info("Site: " + siteForPersistence.toString() + " saved in site repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        siteAdminService.attachNewSiteToAllSiteAdmins(siteForPersistence.getId(), input.getPublisherId());
        return siteForPersistence;
    }

    public Site getById(String id) {
        Site existingSite;
        // TODO: Need a JPA dependency to throw an EntityNotFound exception.
        try {
            existingSite = siteRepository.getById(id);
            LOGGER.info("Retrieved site from site repository: " + existingSite.toString());
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return existingSite;
    }

    public List<Site> findBySiteName(GetSitesByNameInput input) {
        List<Site> matchingSitesByName;
        try {
            matchingSitesByName = siteRepository.findBySiteName(input.getSiteName());
            LOGGER.info("Retrieved sites from site repository: " + matchingSitesByName.toString());
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return matchingSitesByName;
    }

    public List<Site> findBySiteCode(GetSitesByCodeInput input) {
        List<Site> matchingSitesByCode;
        try {
            matchingSitesByCode = siteRepository.findBySiteCode(input.getSiteCode());
            LOGGER.info("Retrieved sites from site repository: " + matchingSitesByCode.toString());
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return matchingSitesByCode;
    }

    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    public List<Site> getSites(GetSitesInput input) {
        Iterable<Site> siteIterator = siteRepository.findAllById(input.getSiteIds());
        List<Site> siteList = StreamSupport
                .stream(siteIterator.spliterator(), false)
                .collect(Collectors.toList());
        return siteList;
    }

    public Site updateSite(UpdateSiteInput input) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        Site updatedSite = getUpdatedSite(input.getId(), input.getUserId());

        Site updatedSiteForPersistence = new Site(
            updatedSite.getId(),
            updatedSite.getCreatedAt(),
            currentTime,
            updatedSite.getEntityTrail(),
            updatedSite.getPublisherId(),
            input.getSiteName(),
            input.getSiteCode(),
            input.getAddress(),
            updatedSite.getUserIds()
        );

        try {
            siteRepository.save(updatedSiteForPersistence);
            LOGGER.info("Site " + updatedSiteForPersistence.toString() + " saved in site repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteForPersistence;
    }

    public Site getUpdatedSite(String existingSiteId, String userId) {
        Site existingSite;
        try {
            existingSite = siteRepository.getById(existingSiteId);
            LOGGER.info("Successfully retrieved site: " + existingSite.toString() + " out of repository to update.");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, userId, getUpdatedSiteSystemComment());

        List<EntityTrail> existingTrail = existingSite.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingSite.setEntityTrail(updatedTrail);

        return existingSite;
    }

    public Site deleteSite(String id) {
        Site deletedSite;
        try {
            deletedSite = siteRepository.getById(id);
            siteRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.info("Entity with id: " + id + " not found in repository");
            throw new RuntimeException(e);
        }
        return deletedSite;
    }

    public void attachUserIdToUserIdList(List<String> existingSiteIds, String userId) {
        List<Site> allSitesUserRegisteredAt = siteRepository.findSitesById(existingSiteIds);
        for (Site site : allSitesUserRegisteredAt) {
            Site updatedSite = getUpdatedSite(site.getId(), userId);
            LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
            List<String> existingUserIdList = updatedSite.getUserIds();
            List<String> newUserIdList = new ArrayList<>(existingUserIdList);
            newUserIdList.add(userId);

            Site updatedSiteForPersistence = new Site(
                    updatedSite.getId(),
                    updatedSite.getCreatedAt(),
                    currentTime,
                    updatedSite.getEntityTrail(),
                    updatedSite.getPublisherId(),
                    updatedSite.getSiteName(),
                    updatedSite.getSiteCode(),
                    updatedSite.getAddress(),
                    newUserIdList
            );

            try {
                siteRepository.save(updatedSiteForPersistence);
                LOGGER.info("Site " + updatedSiteForPersistence.toString() + " saved in site repository");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public void removeAssociatedSiteIdsFromSites(List<String> associatedSiteIds, String userIdToDelete, String userIdWhoDeletedUser) {
        List<Site> associatedSites = siteRepository.findSitesById(associatedSiteIds);
        for (Site site : associatedSites) {
            Site updatedSite = getUpdatedSite(site.getId(), userIdWhoDeletedUser);
            List<String> existingUserIds = updatedSite.getUserIds();
            existingUserIds.remove(userIdToDelete);
            site.setUserIds(existingUserIds);

            try {
                siteRepository.save(updatedSite);
                LOGGER.info("Site: " + updatedSite.toString() + " saved site repository.");
            } catch (Exception e) {
                LOGGER.info(e.toString());
                throw new RuntimeException(e);
            }
        }
    }

    public String getCreatedSiteSystemComment() {
        return "SITE CREATED";
    }

    public String getUpdatedSiteSystemComment() {
        return "SITE UPDATED";
    }

}
