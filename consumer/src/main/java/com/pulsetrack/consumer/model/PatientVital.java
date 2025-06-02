package com.pulsetrack.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patient_vitals")
public class PatientVital {

    @Id
    private String id;
    private String patientId;
    private int heartRate;
    private int bloodPressure;
    private double temperature;
    private Instant timestamp;
}
