package com.pulsetrack.consumer.service;

import com.pulsetrack.consumer.model.PatientVital;
import com.pulsetrack.consumer.repository.PatientVitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PatientVitalConsumer {

    private PatientVitalRepository patientVitalRepository;
    private final AnomalyDetectorService anomalyDetectorService;

    @Autowired
    public PatientVitalConsumer(AnomalyDetectorService anomalyDetectorService, PatientVitalRepository patientVitalRepository) {
        this.anomalyDetectorService = anomalyDetectorService;
        this.patientVitalRepository = patientVitalRepository;
    }

    @KafkaListener(topics = "patient-vitals", groupId = "vital-consumer-group")
    public void consume(PatientVital patientVital) {
        patientVitalRepository.save(patientVital); // save original data
        System.out.println("Consumed and saved: " + patientVital);
        anomalyDetectorService.detectAndSaveAnomalies(patientVital);
    }
}

