package com.cg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.cg.constants.AppConstants;
import com.cg.model.CustomerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class KafkaProducerHelper {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private final ObjectMapper objectMapper = new ObjectMapper();

	public void sendMessage(CustomerDTO message) throws JsonProcessingException {
		kafkaTemplate.send(AppConstants.CUSTOMER_STATUS_TOPIC, objectMapper.writeValueAsString(message));
	}
}
