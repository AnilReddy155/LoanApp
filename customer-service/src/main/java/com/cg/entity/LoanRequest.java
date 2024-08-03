package com.cg.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "loan_request", schema = "loan_cust_db")
public class LoanRequest {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
	@SequenceGenerator(name = "loan_seq", sequenceName = "load_sequence", allocationSize = 1, schema = "bms_cust_db")
	private Long id;

	@Column(name = "customer_id", nullable = false)
	private UUID customerId;

	@Column(name = "loan_type", nullable = false)
	private String loanType; // EDUCATION, PERSONAL, HOME

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "status", nullable = false)
	private String status; // PENDING, APPROVED, REJECTED

	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false)
	private Timestamp updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public void setCustomerId(UUID customerId) {
		this.customerId = customerId;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

}
