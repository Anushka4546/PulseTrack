package com.pulsetrack.producer.service;

import com.pulsetrack.producer.model.PatientVital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.pulsetrack.producer.constants.AppConstants.TOPIC_NAME;

@Service
public class KafkaPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPublisherService.class);

    @Autowired
    private KafkaTemplate<String, PatientVital> kafkaTemplate;

    public void publishVitals(PatientVital vitals) {
        try {
            kafkaTemplate.send(TOPIC_NAME, vitals);
            logger.info("Sent vitals to Kafka: {}", vitals);
        } catch (Exception e) {
            logger.error("Error publishing vitals to Kafka", e.getMessage());
            throw e;
        }
    }
}
