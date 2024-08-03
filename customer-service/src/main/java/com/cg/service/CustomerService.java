package com.cg.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.constants.AppConstants;
import com.cg.entity.Customer;
import com.cg.model.CustomerDTO;
import com.cg.repo.CustomerRepository;
import com.cg.util.KafkaProducerHelper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired 
    private KafkaProducerHelper kafkaProducerHelper;

    public Customer createCustomer(Customer customer) throws Exception {
    	log.info("creating customer with pan number {}", customer.getPan());
    	CustomerDTO customerDTO = new CustomerDTO();
    	customer.setStatus(AppConstants.STATUS_PENDING);
    	Customer savedCustomer = customerRepository.save(customer);
    	BeanUtils.copyProperties(savedCustomer, customerDTO);
    	
    	kafkaProducerHelper.sendMessage(customerDTO);
        return savedCustomer;
    }
    
    
    public void updateCustomerStatus(CustomerDTO customerDTO) {
    	log.info("updating the status for cust id {} of status {}", customerDTO.getId(), customerDTO.getStatus());
    	Optional<Customer> customerById = getCustomerById(customerDTO.getId());
    	if(customerById.isPresent()) {
    		Customer customer = customerById.get();
    		customer.setStatus((AppConstants.STATUS_APPROVED.equalsIgnoreCase(customerDTO.getStatus())) ? AppConstants.STATUS_APPROVED : AppConstants.STATUS_REJECTED);
    		customerRepository.save(customer);
    	}
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer updateCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId())) {
            return customerRepository.save(customer);
        }
        throw new RuntimeException("Customer not found");
    }

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }
}
