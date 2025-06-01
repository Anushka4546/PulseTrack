package com.pulsetrack.producer.service;

import com.pulsetrack.producer.model.PatientVital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.pulsetrack.producer.constants.AppConstants.TOPIC_NAME;

@Service
public class KafkaPublisherService {

    @Autowired
    private KafkaTemplate<String, PatientVital> kafkaTemplate;

    public void publishVitals(PatientVital vitals) {
        kafkaTemplate.send(TOPIC_NAME, vitals);
    }
}
