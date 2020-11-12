package com.cosc436002fitzarr.brm.services;


import com.cosc436002fitzarr.brm.models.EntityTrail;
import com.cosc436002fitzarr.brm.models.riskassessment.RiskAssessment;
import com.cosc436002fitzarr.brm.models.riskassessment.input.CreateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.models.riskassessment.input.UpdateRiskAssessmentInput;
import com.cosc436002fitzarr.brm.repositories.RiskAssessmentRepository;
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
public class RiskAssessmentService {
    @Autowired
    public RiskAssessmentRepository riskAssessmentRepository;

    private static Logger LOGGER = LoggerFactory.getLogger(RiskAssessmentService.class);

    public RiskAssessment createRiskAssessment(CreateRiskAssessmentInput input) {
        String id = UUID.randomUUID().toString();
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        List<EntityTrail> entityTrail = new ArrayList<>();
        entityTrail.add(new EntityTrail(currentTime, input.getPublisherId(), getCreatedRiskAssessmentMessage()));

        RiskAssessment riskAssessmentForPersistence = new RiskAssessment(
                id,
                currentTime,
                currentTime,
                entityTrail,
                input.getPublisherId(),
                input.getWorkOrder(),
                input.getTitle(),
                input.getTaskDescription(),
                input.getHazards(),
                input.getScreeners(),
                input.getSiteIds()
        );

        try {
            riskAssessmentRepository.save(riskAssessmentForPersistence);
            LOGGER.info("Persisted new risk assessment: " + riskAssessmentForPersistence.toString() + " to the risk assessment repository");
        } catch(Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException();
        }
        return riskAssessmentForPersistence;
    }

    public RiskAssessment updateRiskAssessment(UpdateRiskAssessmentInput input) {
        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        RiskAssessment updatedRiskAssessment = getUpdatedRiskAssessment(input.getId(), input.getPublisherId());

        RiskAssessment updatedRiskAssessmentForPersistence = new RiskAssessment(
                updatedRiskAssessment.getId(),
                updatedRiskAssessment.getCreatedAt(),
                currentTime,
                updatedRiskAssessment.getEntityTrail(),
                updatedRiskAssessment.getPublisherId(),
                input.getWorkOrder(),
                input.getTitle(),
                input.getTaskDescription(),
                input.getHazards(),
                input.getScreeners(),
                input.getSiteIds()
        );

        try {
            riskAssessmentRepository.save(updatedRiskAssessmentForPersistence);
            LOGGER.info("Risk assessment " + updatedRiskAssessmentForPersistence.toString() + " saved in risk assessment repository");
        } catch (Exception e) {
            LOGGER.info(e.toString());
            throw new RuntimeException(e);
        }
        return updatedRiskAssessmentForPersistence;
    }

    public RiskAssessment getUpdatedRiskAssessment(String existingRiskAssessmentId, String publisherId) {
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(existingRiskAssessmentId);
        if (!potentialRiskAssessment.isPresent()) {
            LOGGER.info("Risk assessment with id: " + existingRiskAssessmentId + " not found in risk assessment repository");
            throw new EntityNotFoundException();
        }

        RiskAssessment existingRiskAssessment = potentialRiskAssessment.get();
        LOGGER.info("Successfully retrieved risk assessment: " + existingRiskAssessment.toString() + " out of repository to update.");

        LocalDateTime currentTime = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);

        EntityTrail updateTrail = new EntityTrail(currentTime, publisherId, getUpdatedRiskAssessmentMessage());

        List<EntityTrail> existingTrail = existingRiskAssessment.getEntityTrail();

        List<EntityTrail> updatedTrail = new ArrayList<>(existingTrail);

        updatedTrail.add(updateTrail);

        existingRiskAssessment.setEntityTrail(updatedTrail);

        return existingRiskAssessment;
    }

    public RiskAssessment getById(String id) {
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(id);
        Boolean riskAssessmentIsPresent = potentialRiskAssessment.isPresent();
        if (!riskAssessmentIsPresent) {
            LOGGER.info("Risk assessment with id: " + id + " not found in the risk assessment repository!");
            throw new EntityNotFoundException("Risk assessment with id: " + id + " not found in the risk assessment repository!");
        }
        else {
            LOGGER.info("Risk assessment: " + potentialRiskAssessment.get().toString() + " successfully fetched from risk assessment repository");
            return potentialRiskAssessment.get();
        }
    }

    public RiskAssessment deleteRiskAssessment(String id, String publisherId) {
        Optional<RiskAssessment> potentialRiskAssessment = riskAssessmentRepository.findById(id);
        Boolean riskAssessmentIsPresent = potentialRiskAssessment.isPresent();
        if (!riskAssessmentIsPresent) {
            LOGGER.info("Risk assessment with id: " + id + " not found in the risk assessment repository!");
            throw new EntityNotFoundException("Risk assessment with id: " + id + " not found in the risk assessment repository!");
        }
        else {
            riskAssessmentRepository.deleteById(id);
            LOGGER.info("Risk assessment: " + potentialRiskAssessment.get().toString() + " successfully fetched and deleted from risk assessment repository");
            RiskAssessment deletedRiskAssessment = potentialRiskAssessment.get();
            return deletedRiskAssessment;
        }
    }

    public String getCreatedRiskAssessmentMessage() {
        return "CREATED RISK ASSESSMENT";
    }

    public String getUpdatedRiskAssessmentMessage() {
        return "UPDATED RISK ASSESSMENT";
    }
}
