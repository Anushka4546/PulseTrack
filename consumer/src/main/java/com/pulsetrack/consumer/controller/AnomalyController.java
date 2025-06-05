package com.pulsetrack.consumer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pulsetrack.consumer.repository.AnomalyRepository;

import com.pulsetrack.consumer.model.PatientAnomaly;

@RestController
@RequestMapping("/api/v1/anomalies")
public class AnomalyController {
    private final AnomalyRepository anomalyRepository;

    @Autowired
    public AnomalyController(AnomalyRepository anomalyRepository) {
        this.anomalyRepository = anomalyRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<PatientAnomaly>> getAnomalies() {
        return ResponseEntity.ok(anomalyRepository.findAll());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<List<PatientAnomaly>> getAnomaliesByPatientId(@PathVariable String patientId) {
        return ResponseEntity.ok(anomalyRepository.findByPatientId(patientId));
    }
}
