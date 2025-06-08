package com.pulsetrack.consumer.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "patient_anomalies")
public class PatientAnomaly {
    @Id
    private String id;
    private String patientId;
    private Instant timestamp;

    @NotNull
    private String reason;
    private Integer heartRate;
    private Integer bloodPressure;
    private Double temperature;
    
    public PatientAnomaly() {}

    public PatientAnomaly(String patientId, Instant timestamp, String reason,
                            int heartRate, int bloodPressure, Double temperature) {
        this.patientId = patientId;
        this.timestamp = timestamp;
        this.reason = reason;
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
    }
}
