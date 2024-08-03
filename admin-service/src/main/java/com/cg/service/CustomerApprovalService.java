package com.cg.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cg.constants.AppConstants;
import com.cg.entity.CustomerApproval;
import com.cg.model.CustomerDTO;
import com.cg.repo.CustomerApprovalRepository;
import com.cg.util.KafkaProducerHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerApprovalService {

	@Autowired
	private CustomerApprovalRepository customerApprovalRepository;

	@Autowired
	private KafkaProducerHelper kafkaProducerHelper;

	public void processForApproval(CustomerDTO customerDTO) {
		CustomerApproval customerApproval = new CustomerApproval();
		customerApproval.setCustomerId(customerDTO.getId());
		customerApproval.setStatus(AppConstants.STATUS_PENDING);
		customerApproval.setAdminId(1L); // this can be configured that which admin should review this account.
		customerApproval.setCreatedAt(Timestamp.from(Instant.now()));
		customerApproval.setUpdatedAt(Timestamp.from(Instant.now()));

		customerApprovalRepository.save(customerApproval);
	}

	@Scheduled(fixedRate = 500000)
	public void processCustomerApproval() throws Exception {
		log.info("Customer Approval Job Started");
		customerApprovalRepository.findAllByStatus(AppConstants.STATUS_PENDING).stream().map(customerApproval -> {
			// write a logic for customer approval.

			customerApproval.setStatus(AppConstants.STATUS_APPROVED);
			return customerApprovalRepository.save(customerApproval);
		}).map(customerApproval -> {
			CustomerDTO customerDTO = new CustomerDTO();
			customerDTO.setId(customerApproval.getCustomerId());
			customerDTO.setStatus(customerApproval.getStatus());

			return customerDTO;
		}).forEach(customerDTO -> {
			try {
				log.info("cust id {} , Status : {}", customerDTO.getId(), customerDTO.getStatus());
				kafkaProducerHelper.sendMessage(customerDTO);
			} catch (JsonProcessingException e) {
				log.error("Error while approving the request of {}", customerDTO.getId());
			}
		});

		log.info("Customer Approval Job Ended");
	}

	public CustomerApproval createCustomerApproval(CustomerApproval customerApproval) {
		return customerApprovalRepository.save(customerApproval);
	}

	public Optional<CustomerApproval> getCustomerApprovalById(Long id) {
		return customerApprovalRepository.findById(id);
	}

	public CustomerApproval updateCustomerApproval(CustomerApproval customerApproval) {
		if (customerApprovalRepository.existsById(customerApproval.getId())) {
			return customerApprovalRepository.save(customerApproval);
		}
		throw new RuntimeException("Customer Approval not found");
	}

	public void deleteCustomerApproval(Long id) {
		if (customerApprovalRepository.existsById(id)) {
			customerApprovalRepository.deleteById(id);
		} else {
			throw new RuntimeException("Customer Approval not found");
		}
	}

}
