package com.pulsetrack.consumer.repository;

import org.springframework.stereotype.Repository;
import com.pulsetrack.consumer.model.PatientAnomaly;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface AnomalyRepository extends MongoRepository<PatientAnomaly, String> {
    List<PatientAnomaly> findByPatientId(String patientId);
}
