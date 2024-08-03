package com.cg.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cg.constants.AppConstants;
import com.cg.model.CustomerDTO;
import com.cg.service.CustomerApprovalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumerHelper {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private CustomerApprovalService customerApprovalService;

	@KafkaListener(topics = AppConstants.CUSTOMER_REQUEST_TOPIC, groupId = "your-consumer-group")
	public void consume(String message) throws Exception {
		log.info("Received message: " + message);
		customerApprovalService.processForApproval(objectMapper.readValue(message, CustomerDTO.class));
	}
}
