package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.site.Site;
import com.cosc436002fitzarr.brm.models.site.input.*;
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

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteService.class);

    // TODO: Use more helpful exception handling. I.E., should not be catching a generic exception everytime. Be more specific with error handling
    // TODO: Input validation on each API
    public Site createSite(CreateSiteInput input) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String id = UUID.randomUUID().toString();

        List<EntityTrail> entityList = new ArrayList<>();
        entityList.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedSiteSystemComment()));
        Site siteForPersistence = new Site(
            id,
            currentTime,
            currentTime,
            entityList,
            input.getPublisherId(),
            input.getSiteName(),
            input.getSiteCode(),
            input.getAddress()
        );
        Site persistedSite;
        LOGGER.info(siteForPersistence.toString());
        try {
            persistedSite = siteRepository.save(siteForPersistence);
            LOGGER.info("Site: " + persistedSite.toString() + " saved in site repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return persistedSite;
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
        List<String> test = input.getSiteIds()
                .stream()
                .map(ReferenceInput::getId)
                .collect(Collectors.toList());
        LOGGER.info(test.toString());
        List<String> ids = new ArrayList<>();
        for (ReferenceInput reference : input.getSiteIds()) {
            ids.add(reference.getId());
        }
        return siteRepository.findSitesById(ids);
    }

    public Site updateSite(UpdateSiteInput input) {
        Site existingSite;
        try {
            existingSite = siteRepository.getById(input.getReferenceInput().getId());
            LOGGER.info("Successfully retrieved site: " + existingSite.toString() + " out of repository to update.");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, input.getUserId(), getUpdatedSiteSystemComment());

        List<EntityTrail> existingTrail = existingSite.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        Site updatedSiteForPersistence = new Site(
                existingSite.getId(),
                existingSite.getCreatedAt(),
                currentTime,
                updatedTrail,
                existingSite.getPublisherId(),
                input.getSiteName(),
                input.getSiteCode(),
                existingSite.getAddress()
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

    public Site deleteSite(ReferenceInput requestBody) {
        String existingSiteId = requestBody.getId();
        Site deletedSite;
        try {
            deletedSite = siteRepository.getById(existingSiteId);
            siteRepository.deleteById(existingSiteId);
        } catch (Exception e) {
            LOGGER.info("Entity with id: " + existingSiteId + " not found in repository");
            throw new RuntimeException(e);
        }
        return deletedSite;
    }

    public String getCreatedSiteSystemComment() {
        return "SITE CREATED";
    }

    public String getUpdatedSiteSystemComment() {
        return "SITE UPDATED";
    }

}
