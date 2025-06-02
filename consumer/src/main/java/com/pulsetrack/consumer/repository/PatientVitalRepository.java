package com.pulsetrack.consumer.repository;

import com.pulsetrack.consumer.model.PatientVital;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientVitalRepository extends MongoRepository<PatientVital, String> {
}
