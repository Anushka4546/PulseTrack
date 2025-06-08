package com.pulsetrack.consumer.service;

import com.pulsetrack.consumer.model.PatientVital;
import com.pulsetrack.consumer.repository.PatientVitalRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PatientVitalConsumer {

    private static Logger logger = LoggerFactory.getLogger(PatientVital.class);

    private PatientVitalRepository patientVitalRepository;
    private final AnomalyDetectorService anomalyDetectorService;

    private final Validator validator;

    @Autowired
    public PatientVitalConsumer(AnomalyDetectorService anomalyDetectorService, PatientVitalRepository patientVitalRepository) {
        this.anomalyDetectorService = anomalyDetectorService;
        this.patientVitalRepository = patientVitalRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @KafkaListener(topics = "patient-vitals", groupId = "vital-consumer-group")
    public void consume(PatientVital patientVital) {
        logger.info("Received vital for patient {}: {}", patientVital.getPatientId(),patientVital);

        Set<ConstraintViolation<PatientVital>> violations = validator.validate(patientVital);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed for patient ").append(patientVital.getPatientId()).append(": ");
            for (ConstraintViolation<PatientVital> violation : violations) {
                errorMessage.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            logger.warn(errorMessage.toString());
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            patientVitalRepository.save(patientVital);
            logger.info("Vital saved to db for patient {}: {}", patientVital.getPatientId(), patientVital);
             anomalyDetectorService.detectAndSaveAnomalies(patientVital);
        } catch(Exception e) {
            logger.error("Error saving vital to db for patient {}: {}", patientVital.getPatientId(), e.getMessage());
        }
       
    }
}

