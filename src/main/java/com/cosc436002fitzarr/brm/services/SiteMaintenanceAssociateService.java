package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.GetEntityBySiteInput;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.SiteMaintenanceAssociate;
import com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.input.CreateSiteMaintenanceAssociateInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.SiteMaintenanceAssociateRepository;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import com.cosc436002fitzarr.brm.utils.UserAPIHelper;
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
        siteService.attachUserIdToUserIdList(associatedSiteIds, siteMaintenanceAssociateForPersistence.getId());
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

    public List<SiteMaintenanceAssociate> getSiteMaintenanceAssociatesBySite(GetEntityBySiteInput input) {
        return siteMaintenanceAssociateRepository.getSiteMaintenanceAssociatesBySite(input.getAssociatedSiteIds());
    }

    public SiteMaintenanceAssociate getUpdatedSiteMaintenanceAssociate(String id, String userId) {
        SiteMaintenanceAssociate existingSiteMaintenanceAssociate = checkSiteMaintenanceAssociateExists(id);

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updatedTrail = new EntityTrail(currentTime, userId, getSiteMaintenanceAssociateUpdatedSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceAssociate.getEntityTrail();

        existingTrail.add(updatedTrail);

        existingSiteMaintenanceAssociate.setEntityTrail(existingTrail);

        existingSiteMaintenanceAssociate.setUpdatedAt(currentTime);

        return existingSiteMaintenanceAssociate;
    }

    public SiteMaintenanceAssociate updateSiteMaintenanceAssociate(UpdateUserInput input) {
        SiteMaintenanceAssociate existingSiteMaintenanceAssociate = checkSiteMaintenanceAssociateExists(input.getId());

        LOGGER.info("Successfully retrieved site maintenance associate user: " + existingSiteMaintenanceAssociate.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, input.getUserId(), getSiteMaintenanceAssociateUpdatedSystemComment());

        List<EntityTrail> existingTrail = existingSiteMaintenanceAssociate.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        // TODO: If the user updates the site role of the employee, then we will end up storing WHS Members and Site Maintenance Managers in the SiteMaintenanceAssociate repository.
        SiteMaintenanceAssociate updatedSiteMaintenanceAssociateForPersistence = new SiteMaintenanceAssociate(
            existingSiteMaintenanceAssociate.getId(),
            existingSiteMaintenanceAssociate.getCreatedAt(),
            currentTime,
            updatedTrail,
            input.getUserId(),
            existingSiteMaintenanceAssociate.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            existingSiteMaintenanceAssociate.getAuthToken(),
            existingSiteMaintenanceAssociate.getHashPassword(),
            input.getSiteIds(),
            existingSiteMaintenanceAssociate.getSiteMaintenanceManagerId(),
            existingSiteMaintenanceAssociate.getAssignedBuildingRiskAssessmentIds()
        );

        try {
            siteMaintenanceAssociateRepository.save(updatedSiteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociateForPersistence.toString() + " saved in site maintenance associate repository");
            userRepository.save(updatedSiteMaintenanceAssociateForPersistence);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociateForPersistence.toString() + " saved in user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedSiteMaintenanceAssociateForPersistence;
    }

    public SiteMaintenanceAssociate checkSiteMaintenanceAssociateExists(String id) {
        Optional<SiteMaintenanceAssociate> potentialSiteMaintenanceAssociate = siteMaintenanceAssociateRepository.findById(id);
        if (!potentialSiteMaintenanceAssociate.isPresent()) {
            LOGGER.info("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
            throw new EntityNotFoundException("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
        }
        LOGGER.info("Site maintenance associate with id: " + id + " successfully retrieved from site maintenance associate repository");
        return potentialSiteMaintenanceAssociate.get();
    }

    public void assignRiskAssessmentToSiteMaintenanceAssociate(String associateId, String riskAssessmentId, String publisherId) {
        SiteMaintenanceAssociate siteMaintenanceAssociateToUpdate = checkSiteMaintenanceAssociateExists(associateId);

        SiteMaintenanceAssociate updatedSiteMaintenanceAssociate = getUpdatedSiteMaintenanceAssociate(siteMaintenanceAssociateToUpdate.getId(), publisherId);

        List<String> existingAssignedRiskAssessments = updatedSiteMaintenanceAssociate.getAssignedBuildingRiskAssessmentIds();

        existingAssignedRiskAssessments.add(riskAssessmentId);

        updatedSiteMaintenanceAssociate.setAssignedBuildingRiskAssessments(existingAssignedRiskAssessments);

        try {
            siteMaintenanceAssociateRepository.save(updatedSiteMaintenanceAssociate);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociate.toString() + " saved in site maintenance associate repository");
            userRepository.save(updatedSiteMaintenanceAssociate);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociate.toString() + " saved in site maintenance associate repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public void removeAssignedRiskAssessmentFromAssociate(String associateId, String riskAssessmentId, String publisherId) {
        SiteMaintenanceAssociate siteMaintenanceAssociateToUpdate = checkSiteMaintenanceAssociateExists(associateId);

        SiteMaintenanceAssociate updatedSiteMaintenanceAssociate = getUpdatedSiteMaintenanceAssociate(siteMaintenanceAssociateToUpdate.getId(), publisherId);

        List<String> existingAssignedRiskAssessments = updatedSiteMaintenanceAssociate.getAssignedBuildingRiskAssessmentIds();

        existingAssignedRiskAssessments.remove(riskAssessmentId);

        updatedSiteMaintenanceAssociate.setAssignedBuildingRiskAssessments(existingAssignedRiskAssessments);

        try {
            siteMaintenanceAssociateRepository.save(updatedSiteMaintenanceAssociate);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociate.toString() + " saved in site maintenance associate repository");
            userRepository.save(updatedSiteMaintenanceAssociate);
            LOGGER.info("Site maintenance associate: " + updatedSiteMaintenanceAssociate.toString() + " saved in site maintenance associate repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public SiteMaintenanceAssociate deleteSiteMaintenanceAssociate(String id, String userId) {
        UserAPIHelper.checkUserNotDeletingThemselves(id, userId);
        SiteMaintenanceAssociate siteMaintenanceAssociateToDelete = checkSiteMaintenanceAssociateExists(id);

        siteMaintenanceAssociateRepository.deleteById(siteMaintenanceAssociateToDelete.getId());
        userRepository.deleteById(siteMaintenanceAssociateToDelete.getId());
        LOGGER.info("Site maintenance associate: " + siteMaintenanceAssociateToDelete.toString() + " successfully fetched and deleted from site maintenance associate" +
                " and user repositories");
        siteService.removeAssociatedSiteIdsFromSites(siteMaintenanceAssociateToDelete.getAssociatedSiteIds(), siteMaintenanceAssociateToDelete.getId(), userId);
        return siteMaintenanceAssociateToDelete;
    }

    public String getSiteMaintenanceAssociateCreatedSystemComment() {
        return "SITE MAINTENANCE ASSOCIATE CREATED";
    }

    public String getSiteMaintenanceAssociateUpdatedSystemComment() {
        return "SITE MAINTENANCE ASSOCIATE UPDATED";
    }
}
