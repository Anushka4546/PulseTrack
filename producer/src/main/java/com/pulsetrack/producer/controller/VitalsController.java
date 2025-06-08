package com.pulsetrack.producer.controller;

import com.pulsetrack.producer.model.PatientVital;
import com.pulsetrack.producer.service.KafkaPublisherService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
public class VitalsController {

    private static final Logger logger = LoggerFactory.getLogger(VitalsController.class);

    @Autowired
    private KafkaPublisherService publisherService;

    @PostMapping("/{patientId}/vitals")
    public ResponseEntity<?> publishVitals(
            @PathVariable String patientId, 
            @Valid @RequestBody PatientVital patientVital, 
            BindingResult bindingResult) {
        
        if(bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            logger.warn("Validation errors for patient {}: {}", patientId, errors);
            return ResponseEntity.badRequest().body(errors);
        }
        
        patientVital.setPatientId(patientId);
        patientVital.setTimestamp(Instant.now());

        try {
            publisherService.publishVitals(patientVital);
            logger.info("Vitals published for patient {}: {}", patientId, patientVital);
            return ResponseEntity.ok("Vitals published successfully for patient " + patientId);
        } catch (Exception e) {
            logger.error("Error publishing vitals for patient {}: {}", patientId, e.getMessage());
            return ResponseEntity.internalServerError().body("Error publishing vitals for patient " + patientId);
        }
    }
}

