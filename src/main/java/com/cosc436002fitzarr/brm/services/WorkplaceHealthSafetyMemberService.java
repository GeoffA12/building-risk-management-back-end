package com.cosc436002fitzarr.brm.services;

import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.sitemaintenancemanager.SiteMaintenanceManager;
import com.cosc436002fitzarr.brm.models.workplacehealthsafetymember.WorkplaceHealthSafetyMember;
import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;
import com.cosc436002fitzarr.brm.models.user.input.UpdateUserInput;
import com.cosc436002fitzarr.brm.repositories.UserRepository;
import com.cosc436002fitzarr.brm.repositories.WorkplaceHealthSafetyMemberRepository;
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
public class WorkplaceHealthSafetyMemberService {
    @Autowired
    public WorkplaceHealthSafetyMemberRepository workplaceHealthSafetyMemberRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public SiteService siteService;

    private static Logger LOGGER = LoggerFactory.getLogger(WorkplaceHealthSafetyMemberService.class);

    public WorkplaceHealthSafetyMember createWorkPlaceHealthSafetyMember(CreateUserInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        String authToken = UserService.generateNewUserToken();
        String hashedPassword = UserService.createHashedPassword(input.getPassword());

        List<EntityTrail> entityTrail = new ArrayList<>();
        List<String> associatedSiteIds = new ArrayList<>();
        associatedSiteIds.add(input.getSiteId());
        List<String> riskAssessmentFiledIds = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, id, getCreatedWorkplaceHealthSafetyMemberSystemComment()));
        WorkplaceHealthSafetyMember workplaceHealthSafetyMemberForPersistence = new WorkplaceHealthSafetyMember(
            id,
            currentTime,
            currentTime,
            entityTrail,
            id,
            input.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            authToken,
            hashedPassword,
            associatedSiteIds,
            riskAssessmentFiledIds
        );

        try {
            workplaceHealthSafetyMemberRepository.save(workplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Persisted new workplace health and safety member: " + workplaceHealthSafetyMemberForPersistence.toString() + " to the workplace health and safety member repository");
            userRepository.save(workplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Persisted new workplace health and safety member: " + workplaceHealthSafetyMemberForPersistence.toString() + " to the user repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }

        siteService.attachUserIdToUserIdList(associatedSiteIds, workplaceHealthSafetyMemberForPersistence.getId());
        return workplaceHealthSafetyMemberForPersistence;
    }

    public WorkplaceHealthSafetyMember getWorkplaceHealthSafetyMemberById(String id) {
        Optional<WorkplaceHealthSafetyMember> potentialWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberRepository.findById(id);
        Boolean workplaceHealthSafetyMemberPresent = potentialWorkplaceHealthSafetyMember.isPresent();
        if (!workplaceHealthSafetyMemberPresent) {
            LOGGER.info("Workplace health and safety member with id: " + id + " not found in the workplace health and safety repository!");
            throw new EntityNotFoundException("Workplace health and safety member with id: \" + id + \" not found in the workplace health and safety repository!");
        }
        else {
            LOGGER.info("Workplace health and safety member: " + potentialWorkplaceHealthSafetyMember.get().toString() + " successfully " +
                    "fetched from workplace health and safety repository");
            return potentialWorkplaceHealthSafetyMember.get();
        }
    }

    public WorkplaceHealthSafetyMember updateWorkplaceHealthSafetyMember(UpdateUserInput input) {
        Optional<WorkplaceHealthSafetyMember> potentialWorkplaceHealthSafetyMember;

        potentialWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberRepository.findById(input.getId());
        if (!potentialWorkplaceHealthSafetyMember.isPresent()) {
            throw new EntityNotFoundException("Workplace health and safety member with id: " + input.getId() + " not found in workplace health and safety member repository");
        }

        WorkplaceHealthSafetyMember existingWorkplaceHealthSafetyMember = potentialWorkplaceHealthSafetyMember.get();

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        // TODO: Refactor the UpdateUserInput class so that a publisherId in the input object. Otherwise, we have no way of knowing the ID of whoever is updating this
        // specific site admin and can't update the Entity Trail accordingly.
        EntityTrail updateTrail = new EntityTrail(currentTime, input.getUserId(), getUpdatedWorkplaceHealthSafetyMemberSystemComment());

        List<EntityTrail> existingTrail = existingWorkplaceHealthSafetyMember.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        WorkplaceHealthSafetyMember updatedWorkplaceHealthSafetyMemberForPersistence = new WorkplaceHealthSafetyMember(
            existingWorkplaceHealthSafetyMember.getId(),
            currentTime,
            existingWorkplaceHealthSafetyMember.getCreatedAt(),
            updatedTrail,
            input.getUserId(),
            existingWorkplaceHealthSafetyMember.getSiteRole(),
            input.getFirstName(),
            input.getLastName(),
            input.getUsername(),
            input.getEmail(),
            input.getPhone(),
            existingWorkplaceHealthSafetyMember.getAuthToken(),
            existingWorkplaceHealthSafetyMember.getHashPassword(),
            input.getSiteIds(),
            existingWorkplaceHealthSafetyMember.getRiskAssessmentsFiledIds()
        );

        try {
            workplaceHealthSafetyMemberRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health and safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.toString() + " saved in workplace health and " +
                    "safety repository");
            userRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health and safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.getUpdatedAt().toString() + " saved in " +
                    "user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedWorkplaceHealthSafetyMemberForPersistence;
    }

    public WorkplaceHealthSafetyMember deleteWorkplaceHealthSafetyMember(String id, String userId) {
        UserAPIHelper.checkUserNotDeletingThemselves(id, userId);
        Optional<WorkplaceHealthSafetyMember> potentialWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberRepository.findById(id);
        Boolean workplaceHealthSafetyMemberPresent = potentialWorkplaceHealthSafetyMember.isPresent();
        if (!workplaceHealthSafetyMemberPresent) {
            LOGGER.info("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
            throw new EntityNotFoundException("Site maintenance associate with id: " + id + " not found in the site maintenance associate repository!");
        } else {
            workplaceHealthSafetyMemberRepository.deleteById(id);
            userRepository.deleteById(id);
            LOGGER.info("Workplace health and safety member: " + potentialWorkplaceHealthSafetyMember.get().toString() + " successfully fetched and " +
                    "deleted from workplace health and safety and user repositories");
            WorkplaceHealthSafetyMember deletedWorkplaceHealthSafetyMember = potentialWorkplaceHealthSafetyMember.get();
            siteService.removeAssociatedSiteIdsFromSites(deletedWorkplaceHealthSafetyMember.getAssociatedSiteIds(), deletedWorkplaceHealthSafetyMember.getId(), userId);
            return deletedWorkplaceHealthSafetyMember;
        }
    }

    public void attachRiskAssessmentIdToWorkplaceHealthSafetyMemberIdList(String riskAssessmentId, String existingWorkplaceHealthSafetyMemberId) {
        Optional<WorkplaceHealthSafetyMember> potentialWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberRepository.findById(existingWorkplaceHealthSafetyMemberId);
        if (!potentialWorkplaceHealthSafetyMember.isPresent()) {
            throw new EntityNotFoundException("Workplace Health Safety Member with id: " + existingWorkplaceHealthSafetyMemberId + " not found in workplace health safety member repository!");
        }

        WorkplaceHealthSafetyMember existingWorkplaceHealthSafetyMember = potentialWorkplaceHealthSafetyMember.get();
        LOGGER.info("Successfully retrieved workplace health safety member: " + existingWorkplaceHealthSafetyMember.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, existingWorkplaceHealthSafetyMember.getId(), getUpdatedWorkplaceHealthSafetyMemberSystemComment());

        List<EntityTrail> existingTrail = existingWorkplaceHealthSafetyMember.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        List<String> updatedRiskAssessmentsFiledIds = new ArrayList<>(existingWorkplaceHealthSafetyMember.getRiskAssessmentsFiledIds());

        updatedRiskAssessmentsFiledIds.add(riskAssessmentId);

        WorkplaceHealthSafetyMember updatedWorkplaceHealthSafetyMemberForPersistence = new WorkplaceHealthSafetyMember(
                existingWorkplaceHealthSafetyMember.getId(),
                currentTime,
                existingWorkplaceHealthSafetyMember.getCreatedAt(),
                updatedTrail,
                existingWorkplaceHealthSafetyMember.getId(),
                existingWorkplaceHealthSafetyMember.getSiteRole(),
                existingWorkplaceHealthSafetyMember.getFirstName(),
                existingWorkplaceHealthSafetyMember.getLastName(),
                existingWorkplaceHealthSafetyMember.getUsername(),
                existingWorkplaceHealthSafetyMember.getEmail(),
                existingWorkplaceHealthSafetyMember.getPhone(),
                existingWorkplaceHealthSafetyMember.getAuthToken(),
                existingWorkplaceHealthSafetyMember.getHashPassword(),
                existingWorkplaceHealthSafetyMember.getAssociatedSiteIds(),
                updatedRiskAssessmentsFiledIds
        );

        try {
            workplaceHealthSafetyMemberRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.toString() + " saved in workplace health safety member repository");
            userRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.toString() + " saved in user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public void removeRiskAssessmentIdFromWorkplaceHealthSafetyMemberIdList(String riskAssessmentId, String existingWorkplaceHealthSafetyMemberId) {
        Optional<WorkplaceHealthSafetyMember> potentialWorkplaceHealthSafetyMember = workplaceHealthSafetyMemberRepository.findById(existingWorkplaceHealthSafetyMemberId);
        if (!potentialWorkplaceHealthSafetyMember.isPresent()) {
            throw new EntityNotFoundException("Workplace Health Safety Member with id: " + existingWorkplaceHealthSafetyMemberId + " not found in workplace health safety member repository!");
        }

        WorkplaceHealthSafetyMember existingWorkplaceHealthSafetyMember = potentialWorkplaceHealthSafetyMember.get();
        LOGGER.info("Successfully retrieved workplace health safety member: " + existingWorkplaceHealthSafetyMember.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, existingWorkplaceHealthSafetyMember.getId(), getUpdatedWorkplaceHealthSafetyMemberSystemComment());

        List<EntityTrail> existingTrail = existingWorkplaceHealthSafetyMember.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        List<String> updatedRiskAssessmentsFiledIds = new ArrayList<>(existingWorkplaceHealthSafetyMember.getRiskAssessmentsFiledIds());

        updatedRiskAssessmentsFiledIds.remove(riskAssessmentId);

        WorkplaceHealthSafetyMember updatedWorkplaceHealthSafetyMemberForPersistence = new WorkplaceHealthSafetyMember(
                existingWorkplaceHealthSafetyMember.getId(),
                currentTime,
                existingWorkplaceHealthSafetyMember.getCreatedAt(),
                updatedTrail,
                existingWorkplaceHealthSafetyMember.getId(),
                existingWorkplaceHealthSafetyMember.getSiteRole(),
                existingWorkplaceHealthSafetyMember.getFirstName(),
                existingWorkplaceHealthSafetyMember.getLastName(),
                existingWorkplaceHealthSafetyMember.getUsername(),
                existingWorkplaceHealthSafetyMember.getEmail(),
                existingWorkplaceHealthSafetyMember.getPhone(),
                existingWorkplaceHealthSafetyMember.getAuthToken(),
                existingWorkplaceHealthSafetyMember.getHashPassword(),
                existingWorkplaceHealthSafetyMember.getAssociatedSiteIds(),
                updatedRiskAssessmentsFiledIds
        );

        try {
            workplaceHealthSafetyMemberRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.toString() + " saved in workplace health safety member repository");
            userRepository.save(updatedWorkplaceHealthSafetyMemberForPersistence);
            LOGGER.info("Workplace health safety member: " + updatedWorkplaceHealthSafetyMemberForPersistence.toString() + " saved in user repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
    }

    public List<WorkplaceHealthSafetyMember> getWorkplaceHealthSafetyMembersByUserIdAndSite(String userId, List<String> associatedSiteIds) {
        return workplaceHealthSafetyMemberRepository.getWorkplaceHealthSafetyMembersByUserIdAndSite(userId, associatedSiteIds);
    }

    public String getCreatedWorkplaceHealthSafetyMemberSystemComment() {
        return "CREATED WORKPLACE HEALTH AND SAFETY MEMBER";
    }

    public String getUpdatedWorkplaceHealthSafetyMemberSystemComment() {
        return "UPDATED WORKPLACE HEALTH AND SAFETY MEMBER";
    }
}
