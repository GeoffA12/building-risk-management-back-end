package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.user.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.models.user.input.CreateSiteMaintenanceAssociateInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.SiteMaintenanceAssociateRepository;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SiteMaintenanceAssociateService {
    @Autowired
    public SiteMaintenanceAssociateRepository siteMaintenanceAssociateRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteService siteService;
    @Autowired
    public SiteMaintenanceManagerService siteMaintenanceManagerService;

    private static Logger LOGGER = LoggerFactory.getLogger(SiteMaintenanceAssociateService.class);

    public SiteMaintenanceAssociate createSiteMaintenanceAssociate(CreateSiteMaintenanceAssociateInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String authToken = UserService.generateNewUserToken();
        String hashedPassword = UserService.createHashedPassword(input.getCreateUserInput().getPassword());

        List<EntityTrail> entityTrail = new ArrayList<>();
        List<String> associatedSiteIds = new ArrayList<>();
        associatedSiteIds.add(input.getCreateUserInput().getSiteId());

        EntityTrail createdEntity = new EntityTrail(currentTime, id, getSiteMaintenanceAssociateCreatedSystemComment());
        entityTrail.add(createdEntity);

        List<String> assignedBuildingRiskAssessmentIds = new ArrayList<>();

        SiteMaintenanceAssociate siteMaintenanceAssociateForPersistence = new SiteMaintenanceAssociate(
            id,
            currentTime,
            currentTime,
            entityTrail,
            id,
            input.getCreateUserInput().getSiteRole(),
            input.getCreateUserInput().getFirstName(),
            input.getCreateUserInput().getLastName(),
            input.getCreateUserInput().getUsername(),
            input.getCreateUserInput().getEmail(),
            input.getCreateUserInput().getPhone(),
            authToken,
            hashedPassword,
            associatedSiteIds,
            input.getSiteMaintenanceManagerId(),
            assignedBuildingRiskAssessmentIds
        );

        try {
            siteMaintenanceAssociateRepository.save(siteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + siteMaintenanceAssociateForPersistence.toString() + " successfully added to site maintenance associate repository");
            userRepository.save(siteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + siteMaintenanceAssociateForPersistence.toString() + " successfully added to user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        siteService.attachUserIdToUserIdList(input.getCreateUserInput().getSiteId(), siteMaintenanceAssociateForPersistence.getId());
        siteMaintenanceManagerService.attachSiteMaintenanceAssociateIdToSiteMaintenanceManagerIdList(siteMaintenanceAssociateForPersistence.getId(), input.getSiteMaintenanceManagerId());
        return siteMaintenanceAssociateForPersistence;
    }

    public SiteMaintenanceAssociate getById(String id) {
        Optional<SiteMaintenanceAssociate> potentialSiteMaintenanceAssociate = siteMaintenanceAssociateRepository.findById(id);
        Boolean siteMaintenanceAssociateIsPresent = potentialSiteMaintenanceAssociate.isPresent();
        if (!siteMaintenanceAssociateIsPresent) {
            LOGGER.info("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
            throw new EntityNotFoundException("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
        }
        else {
            LOGGER.info("Site maintenance associate: " + potentialSiteMaintenanceAssociate.get().toString() + " successfully fetched from site maintenance associate repository");
            return potentialSiteMaintenanceAssociate.get();
        }
    }

    public SiteMaintenanceAssociate updateSiteMaintenanceAssociate(UpdateUserInput input) {
        Optional<SiteMaintenanceAssociate> potentialSiteMaintenanceAssociate;

        potentialSiteMaintenanceAssociate = siteMaintenanceAssociateRepository.findById(input.getId());
        if (!potentialSiteMaintenanceAssociate.isPresent()) {
            throw new EntityNotFoundException("Site Maintenance Associate with id: " + input.getId() + " not found in site maintenance repository!");
        }

        SiteMaintenanceAssociate existingSiteMaintenanceAssociate = potentialSiteMaintenanceAssociate.get();
        LOGGER.info("Successfully retrieved site maintenance associate user: " + existingSiteMaintenanceAssociate.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, existingSiteMaintenanceAssociate.getId(), getSiteMaintenanceAssociateUpdatedSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceAssociate.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        SiteMaintenanceAssociate updatedSiteMaintenanceAssociateForPersistence = new SiteMaintenanceAssociate(
            existingSiteMaintenanceAssociate.getId(),
            currentTime,
            existingSiteMaintenanceAssociate.getCreatedAt(),
            updatedTrail,
            existingSiteMaintenanceAssociate.getId(),
            input.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            input.getAuthToken(),
            existingSiteMaintenanceAssociate.getHashPassword(),
            existingSiteMaintenanceAssociate.getAssociatedSiteIds(),
            existingSiteMaintenanceAssociate.getSiteMaintenanceManagerId(),
            existingSiteMaintenanceAssociate.getAssignedBuildingRiskAssessmentIds()
        );

        try {
            siteMaintenanceAssociateRepository.save(updatedSiteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociateForPersistence.toString() + " saved in site maintenance associate repository");
            userRepository.save(updatedSiteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociateForPersistence.toString() + " saved in site maintenance associate repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteMaintenanceAssociateForPersistence;
    }

    public SiteMaintenanceAssociate deleteSiteMaintenanceAssociate(String id) {
        Optional<SiteMaintenanceAssociate> potentialSiteMaintenanceAssociate = siteMaintenanceAssociateRepository.findById(id);
        Boolean siteMaintenanceAssociatePresent = potentialSiteMaintenanceAssociate.isPresent();
        if (!siteMaintenanceAssociatePresent) {
            LOGGER.info("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
            throw new EntityNotFoundException("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
        }
        else {
            siteMaintenanceAssociateRepository.deleteById(id);
            userRepository.deleteById(id);
            LOGGER.info("Site maintenance associate: " + potentialSiteMaintenanceAssociate.get().toString() + " successfully fetched and deleted from site maintenance associate" +
                    " and user repositories");
            return potentialSiteMaintenanceAssociate.get();
        }
    }

    public String getSiteMaintenanceAssociateCreatedSystemComment() {
        return "SITE MAINTENANCE ASSOCIATE CREATED";
    }

    public String getSiteMaintenanceAssociateUpdatedSystemComment() {
        return "SITE MAINTENANCE ASSOCIATE UPDATED";
    }
}
