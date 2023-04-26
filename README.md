# Account Service API overview
- Account service application is to create new current account for existing customer using customer id and initial credit amount.
- Handled operations : Create current account, view current account detail and persist transaction if initial credit is greater than zero.
- Build and deploy application using CI/CD pipeline in Azure Kubernetes Cluster

## Technology Stack
- Java 11
- Spring Boot 2.7.9
- Junit 5.8.2
- Wiremock
- Database H2

## Architectural Design
![Architectural Design](images/ArchDesign.png)
## CI/CD Flow
![CI/CD Flow](images/cicdflow.png)

## Entity Relationship Diagram
![Entity Relationship Diagram](images/ERDiagram.png)

## Code Coverage
![Code Coverage](images/codecoverage.png)

## Application Running Instructions - Both Account Service & Transaction Service
 
  - How to run application in local
    - mvn clean install
    - mvn spring-boot:run
  - Run application using CI/CD pipeline
  	- Create one Kubernete cluster in Microsoft azure account
  		- Resource group name :  kubernetes-rg
  		- Cluster name: kubernetes-cluster
  		- To Connect to the cluster run the commands as shown in below screenshot. It will create config file inside user directory of local system
  			Example:  C:\Users\<username>\.kube\config
  		- Copy this config file content to create service connection for kubernetes in azure 		
  			- ![Resource](images/kubernetesvcconnection.png) 			
  		- Create Service connection for git repository to run pipeline
  		- Create Service connection for Docker registry to push image into registry
  			- Replace with your docker registry name account, transaction service in configuration/azure/account-pipeline.yml , transaction-pipeline.yml in Line No. 21,22
  			- Replace with your docker registry name account, transaction service in configuration/azure/deployment.yaml in Line No. 25
  		- To Create New Pipeline for transaction service in azure 
  		     - Pipelines -> New Pipeline -> Select GitHub-> select transaction repository-> Select Use Existing Azure Pipeline YAML->select configuration/azure/transaction-pipeline.yml -> Run
  		   		- You can see status of the pipeline
  		    	- ![Resource](images/transactionpipelinesuccess.png)  
  		- To Create New Pipeline for account service in azure 
  		     - Pipelines -> New Pipeline -> Select GitHub-> select account repository-> Select Use Existing Azure Pipeline YAML->select configuration/azure/account-pipeline.yml -> Run
  		   		- You can see status of the pipeline
  		    	- ![Resource](images/accountpipelinesuccess.png) 
  		- Execute below command in cloud shell 
  			- kubectl get svc
  				 - ![Resource](images/k8sservice.png)
  			- Copy the EXTERNAL-IP of account service and port is 8003
  				- URL sample: http://20.84.12.77:8003/swagger-ui/
  				- ![Resource](images/accountresource.png)
			- Copy the EXTERNAL-IP of transaction service and port is 8004
				- URL sample: http://20.85.249.179:8004/swagger-ui/
				- ![Resource](images/transactionresource.png)
			- To check log execute below command 
				- kubectl get pods
				- kubectl logs -f <podnameofaccount>
				- kubectl logs -f <podnameoftransaction>
				
		- Its mandatory to run both account, transaction service to do create/ get operation
  			- Customer Id available are 1 , 2
  			- Create new account of customer id : 1 with initial credit some amount
  				- ![Resource](images/createaccountsuccess.png)
  			- Create new account with initial credit 0
  			- Get current of the customer 1
  				- ![Resource](images/getaccountdetailsuccess.png)
  				
## Monitoring application - Actuator
 - URL example
	- http://20.85.249.179:8004/actuator/health
	- http://20.84.12.77:8003/actuator/health
	 		      	
## Swagger Documentation
 - [Application URL](http://<hostname>:8004/swagger-ui/) (Prerequisite: The application should be running on port number : 8004, hostname should be EXTERNAL-IP/localhost)
![Resource](images/accountresource.png)

## Accessing H2 Database
 - [Database URL](http://localhost:8004/h2)  (Prerequisite: The application should be running on port number : 8004 and can only be accessed in localhost)

## Initial Data
 - On startup application will load initial data. You can Add/Modify existing data in src/main/resources/data.sql.
 - Tables branch,  account_type, address and customer are loaded with initial data. Please note that these are mandatory data otherwise application will throw internal server exception.
 
## Achieved Functionalities
   - Create new Current Account and its transaction.
   - Transaction service is accessed from account service to save account transaction details and to retrieve the same.
   - Get saved Current account by customer id.
   - While creating new current account it will invoke transaction service to store transaction of that account.
   	 If transaction fails/unable to connect transaction service then created account will be deleted.
   - Circuit breaker pattern is applied to the resource in account service -Minimum number of call is set to 5
   - Integration Test (AccountControllerIntegrationTest.java)
   - Unit test 
   - Created CI/CD pipeline to deploy application in Azure Kubernetes service
   - Added actuator to monitor application
   - Swagger Documentation
   