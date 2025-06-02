package com.pulsetrack.consumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pulsetrack.consumer.model.PatientAnomaly;
import com.pulsetrack.consumer.model.PatientVital;
import com.pulsetrack.consumer.repository.AnomalyRepository;

@Service
public class AnomalyDetectorService {
    private final AnomalyRepository anomalyRepository;

    @Autowired
    public AnomalyDetectorService(AnomalyRepository anomalyRepository) {
        this.anomalyRepository = anomalyRepository;
    }

    public void detectAndSaveAnomalies(PatientVital patientVital) {
        boolean isAnomaly = false;
        String anomalyReason = "";

        if(patientVital.getHeartRate() < 50 || patientVital.getHeartRate() > 120) {
            isAnomaly = true;
            anomalyReason += "Abnormal Heart Rate";
        }

        if(patientVital.getBloodPressure() < 80 || patientVital.getBloodPressure() > 140) {
            isAnomaly = true;
            anomalyReason += "Abnormal Blood Pressure";
        }

        if(patientVital.getTemperature() < 96.0 || patientVital.getTemperature() > 100.5) {
            isAnomaly = true;
            anomalyReason += "Abnormal Temperature";
        }

        if(isAnomaly) {
            PatientAnomaly anomaly = new PatientAnomaly(
                patientVital.getPatientId(),
                patientVital.getTimestamp(),
                anomalyReason,
                patientVital.getHeartRate(),
                patientVital.getBloodPressure(),
                patientVital.getTemperature()
            );

            anomalyRepository.save(anomaly);
        }
    }

}
