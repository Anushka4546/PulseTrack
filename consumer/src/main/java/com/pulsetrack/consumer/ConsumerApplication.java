package com.pulsetrack.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner {

	@Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrap;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Kafka Bootstrap servers: " + kafkaBootstrap);
    }
}
