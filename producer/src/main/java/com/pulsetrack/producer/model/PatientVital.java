package com.pulsetrack.producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientVital {
    private String patientId;

    @NotNull
    @Min(30)
    @Max(250)
    private Integer heartRate;

    @NotNull
    @Min(60)
    @Max(200)
    private Integer bloodPressure;

    @NotNull
    @DecimalMin("90.0")
    @DecimalMax("110.0")
    private Double temperature;

    private Instant timestamp;
}
