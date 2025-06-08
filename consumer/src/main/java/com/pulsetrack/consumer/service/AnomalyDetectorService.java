package com.pulsetrack.consumer.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulsetrack.consumer.model.PatientAnomaly;
import com.pulsetrack.consumer.model.PatientVital;
import com.pulsetrack.consumer.repository.AnomalyRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
public class AnomalyDetectorService {
    private static final Logger logger = LoggerFactory.getLogger(AnomalyDetectorService.class);
    private final AnomalyRepository anomalyRepository;
    private final Validator validator;

    @Autowired
    public AnomalyDetectorService(AnomalyRepository anomalyRepository) {
        this.anomalyRepository = anomalyRepository;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void detectAndSaveAnomalies(PatientVital patientVital) {
        Set<ConstraintViolation<PatientVital>> violations = validator.validate(patientVital);
        if (!violations.isEmpty()) {
            StringBuilder errorMsg = new StringBuilder("Validation errors in PatientVital: ");
            for (ConstraintViolation<PatientVital> violation : violations) {
                errorMsg.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            logger.warn(errorMsg.toString());
            return;
        }

        boolean isAnomaly = false;
        String anomalyReason = "";

        if(patientVital.getHeartRate() < 50 || patientVital.getHeartRate() > 120) {
            isAnomaly = true;
            anomalyReason += "Abnormal Heart Rate ";
        }

        if(patientVital.getBloodPressure() < 80 || patientVital.getBloodPressure() > 140) {
            isAnomaly = true;
            anomalyReason += "Abnormal Blood Pressure ";
        }

        if(patientVital.getTemperature() < 96.0 || patientVital.getTemperature() > 100.5) {
            isAnomaly = true;
            anomalyReason += "Abnormal Temperature";
        }

        if(isAnomaly) {
            logger.warn("Anomaly detected for patient {}: {}", patientVital.getPatientId(), anomalyReason);
            PatientAnomaly anomaly = new PatientAnomaly(
                patientVital.getPatientId(),
                patientVital.getTimestamp(),
                anomalyReason,
                patientVital.getHeartRate(),
                patientVital.getBloodPressure(),
                patientVital.getTemperature()
            );
            
            try {
                anomalyRepository.save(anomaly);
                logger.info("Anomaly saved to db for patient {}: {}", patientVital.getPatientId(), patientVital);
            } catch(Exception e) {
                logger.error("Error saving vital to db for patient {}: {}", patientVital.getPatientId(), e.getMessage());
            }
        }
    }

}
