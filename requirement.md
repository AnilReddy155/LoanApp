"# LoanApp" 

# Requirement

Develop the Microservices Bank Application using java17/21, Spring Cloud Framework.
Loan Application.
1.	Customer MS  - customer creation request/password (Primary key is PAN#), loan request form to apply for different loans (Education, personal, Home)
2.	Admin MS 
    a.	– Loans: Approve, Reject based on “Loan application validation MS” output.
    b.	– Customer : customer creation request Approval, password reset for customer.
    c.	– Reports: list pending Loans, List loans based on user.
3.	Loan application Validation MS (check cebil, payment history) (Create some Sample date using PAN)  and send response as success or failure.

Acceptance criteria:
1.	Customer MS and Admin MS need to communicate in async way (Kafak).
2.	Admin MS and Loan application Validation MS need to communicate via Rest.
3.	Use JPA to authenticate the all the MS.
4.	Use Spring Discovery service and config service. Refer your DS training.
5.	Use JPA for DB along with transactional manager where ever is required.
6.	Implement a singleton class in customer MS.
7.	Implement fallback in Admin MS while calling Loan application Validation.
  All MS should contain:
1.	implement Global Exception Handling.
2.	code coverage should be 80% +.
3.	Use more recent features in 17/21 and use very less features in java 8 where required.
4.	No for loops or more if conditions.
Other Learning:
1.	Docker
2.	Kubernetes
3.	Git command.


Data Base Model for Each Microservice:
# Customer Microservice
`CREATE TABLE customer (
    pan_number VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(255),
    phone_number VARCHAR(15),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE loan_application (
    loan_id UUID PRIMARY KEY,
    pan_number VARCHAR(10),
    loan_type VARCHAR(50),
    amount DECIMAL(15, 2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pan_number) REFERENCES customer(pan_number)
);`

# Admin Microservice

CREATE TABLE customer_approval (
    approval_id UUID PRIMARY KEY,
    pan_number VARCHAR(10),
    status VARCHAR(50),
    approved_by VARCHAR(100), // admin id or name
    approved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (pan_number) REFERENCES customer(pan_number)
);

CREATE TABLE loan_approval (
    approval_id UUID PRIMARY KEY,
    loan_id UUID,
    status VARCHAR(50),
    approved_by VARCHAR(100),
    approved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (loan_id) REFERENCES loan_application(loan_id)
);


# Loan Application Validation Microservice (Loan Application Validation MS):

CREATE TABLE customer_credit_info (
    pan_number VARCHAR(10) PRIMARY KEY,
    credit_score INT,
    payment_history TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);





1. Customer Microservice (Customer MS)
    customer table:

    This table stores information about customers, identified uniquely by their PAN number.
    It includes basic details such as name, email, password (encrypted), phone number, and address.
    The timestamps created_at and updated_at help track when the customer was created and last updated.
    loan_application table:

    This table stores loan applications submitted by customers. Each application is identified by a unique loan_id.
    The pan_number serves as a foreign key linking the loan application to the specific customer in the customer table.
    loan_type indicates the type of loan (e.g., "Education", "Personal", "Home").
    amount represents the requested loan amount.
    status indicates the state of the loan application (e.g., "Pending", "Approved", "Rejected").
    Timestamps created_at and updated_at track the submission and last modification times.
2. Admin Microservice (Admin MS)
    customer_approval table:

    This table manages the approval process of new customers. Each approval request is identified by a unique approval_id.
    The pan_number is a foreign key referencing the customer being approved.
    The status field reflects whether the request is "Pending", "Approved", or "Rejected".
    approved_by and approved_at store the details of the admin who approved the customer.
    Timestamps help track when the approval request was created and last updated.
    loan_approval table:

    This table manages the approval process for loan applications. Each approval request is identified by approval_id.
    The loan_id references the specific loan application from the loan_application table.
    The status field indicates whether the loan is "Pending", "Approved", or "Rejected".
    approved_by and approved_at capture details of the admin who handled the loan approval.
    Timestamps track when the approval request was created and last updated.
3. Loan Application Validation Microservice (Loan Application Validation MS)
    customer_credit_info table:
    This table stores credit information for customers, identified by pan_number.
    It includes the customer's credit_score and payment_history, which are crucial for validating loan applications.
    Timestamps created_at and updated_at help track the data entry and modifications.
# Data Flow Between Microservices
1. Customer Registration and Loan Application:
    A customer registers through the Customer MS, and their details are stored in the customer table.
    The registration request is sent asynchronously via Kafka to the Admin MS for approval.
    The Admin MS updates the customer_approval table with the approval status and sends the result back to the Customer MS via Kafka.
    The customer can apply for a loan, which is recorded in the loan_application table. The loan application is then forwarded to the Admin MS for processing.
2. Loan Application Processing:
    When a loan application is received, the Admin MS calls the Loan Application Validation MS via REST to validate the customer's credit information.
    The Loan Application Validation MS retrieves the relevant data from the customer_credit_info table and returns the validation result ("Success" or "Failure").
    Based on the validation result, the Admin MS updates the loan application's status in the loan_application table and records the decision in the loan_approval table.
3. Handling Loan Approvals and Customer Requests:
    The Admin MS manages customer creation requests and loan applications. The approval process is tracked in the customer_approval and loan_approval tables.
    The Admin MS can also handle password reset requests and manage customer-related tasks.
# Data Flow Example:
1. Customer Registration:

    Customer MS: A customer submits their registration details.
    Admin MS: Receives the request via Kafka, approves or rejects it, and updates the customer_approval table.
    Customer MS: Receives the approval status via Kafka and updates the customer status accordingly.
    Loan Application:

    Customer MS: A customer applies for a loan.
    Admin MS: Receives the loan application, sends a validation request to Loan Application Validation MS.
    Loan Application Validation MS: Validates the customer's credit score and returns a response.
    Admin MS: Updates the loan status based on the validation and records the decision in the loan_approval table.
    Customer MS: The customer is notified of the loan application's status.
Transactional Management:
    JPA Transactions: Ensure that all database operations within a service are atomic. If any step fails (e.g., updating the loan status), the entire transaction can be rolled back to maintain data consistency.

    Global Exception Handling: Implementing global exception handling ensures that any exceptions thrown during the process are caught and handled gracefully.

