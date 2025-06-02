package com.pulsetrack.producer.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientVital {
    private String patientId;
    private int heartRate;
    private int bloodPressure;
    private double temperature;
    private Instant timestamp;
}
