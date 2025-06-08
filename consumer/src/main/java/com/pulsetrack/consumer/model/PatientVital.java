package com.pulsetrack.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patient_vitals")
public class PatientVital {

    @Id
    private String id;
    private String patientId;

    @NotNull
    @Min(30)
    @Max(250)
    private int heartRate;

    @NotNull
    @Min(60)
    @Max(200)
    private int bloodPressure;

    @NotNull
    @Min(90)
    @Max(110)
    private double temperature;
    
    private Instant timestamp;
}
