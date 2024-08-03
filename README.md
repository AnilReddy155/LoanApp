'# LOAN APP'

To Start Application for need to run a kafka.
1. Go to kafka instalattion directory.
2. run Zookeeper using below command
    `C:\kafka>bin\windows\zookeeper-server-start.bat config\zookeeper.properties`
3. run Kafka server using below command
    `C:\kafka>bin\windows\kafka-server-start.bat config\server-0.properties` 
4. make sure to verify the `server-0.properties` peresent and it is refering correct zookeeper.       




# CUSTOMER MICROSERVICE

Create DB Schemas

create SChema loan_cust_db;

CREATE SEQUENCE IF NOT EXISTS loan_cust_db.customer_sequence
	INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
	
CREATE SEQUENCE IF NOT EXISTS loan_cust_db.load_sequence
	INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;	




# ADMIN MICROSERVICE

Create DB Schemas

create SChema admin_db;

CREATE SEQUENCE IF NOT EXISTS admin_db.customer_approval_sequence
	INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
	
CREATE SEQUENCE IF NOT EXISTS admin_db.loan_approval_sequence
	INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;
	
	
CREATE SEQUENCE IF NOT EXISTS admin_db.report_sequence
	INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;	

