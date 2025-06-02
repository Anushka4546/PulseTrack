package com.pulsetrack.producer.controller;

import com.pulsetrack.producer.model.PatientVital;
import com.pulsetrack.producer.service.KafkaPublisherService;

import java.time.Instant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class VitalsController {

    @Autowired
    private KafkaPublisherService publisherService;

    @PostMapping("/{patientId}/vitals")
    public String publishVitals(@PathVariable String patientId, @RequestBody PatientVital patientVital) {
        patientVital.setPatientId(patientId);
        patientVital.setTimestamp(Instant.now());
        publisherService.publishVitals(patientVital);
        return "Vitals published successfully for patient " + patientId;
    }
}

