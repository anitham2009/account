# Account Service API overview
- Account service application is to create new current with initial credit amount and store its transaction based upon initial credit amount, view the stored data.
- Handled operations : create,get newly created current account. Delete newly current account if relevant accounts transaction fails.

## Technology Stack
- Java 11
- Spring Boot 2.7.9
- Junit 5.8.2
- Wiremock
- Database H2

## Architectural Design
![Architectural Design](images/ERDiagram.png)

## Entity Relationship Diagram
![Code Coverage](images/RecipeServiceERD.png)

## Code Coverage
![Entity Relationship Diagram](images/codecoverage.png)

## Application Running Instructions
 
  - How to run application in local
    - mvn clean install
    - mvn spring-boot:run
  - Run jar using below command
  	- java -jar <jar-location>\account-1.0.jar
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
  		- To Create New Pipeline of transaction service in azure 
  		     Pipeline -> New Pipeline -> Select GitHub-> select transaction repository-> Select Use Existing Azure Pipeline YAML->select configuration/azure/transaction-pipeline.yml -> Run
  		   		- You can see status of the pipeline
  		    	- ![Resource](images/transactionpipelinesuccess.png)  
  		- To Create New Pipeline of account service in azure 
  		     Pipeline -> New Pipeline -> Select GitHub-> select account repository-> Select Use Existing Azure Pipeline YAML->select configuration/azure/account-pipeline.yml -> Run
  		   		- You can see status of the pipeline
  		    	- ![Resource](images/accountpipelinesuccess.png) 
  		- To get the URL to run the application execute below command in cloud shell/ local if kubectl installed in local
  			- kubectl get svc
  				 - ![Resource](images/k8sservice.png)
  			- Copy the ip address of account service in EXTERNAL-IP and port 8003
  				- URL sample: http://20.84.12.77:8003/swagger-ui/
  				- ![Resource](images/accountresource.png)
			- Copy the ip address of account service in EXTERNAL-IP and port 8003
				- URL sample: http://20.85.249.179:8004/swagger-ui/
				- ![Resource](images/transactionresource.png)
		- Its mandatory to run both account, transaction service to do create/ get operation
  			- Customer Id available are 1 , 2
  			- Create new account of customer id : 1 with initial credit some amount
  				- ![Resource](images/createaccountsuccess.png)
  			- Create new account with initial credit 0
  			- Get current of the customer 1
  				- ![Resource](images/getaccountdetailsuccess.png)
  			 		      	
## Swagger Documentation
 - [Application URL](http://<hostname>:8003/recipeservice/swagger-ui/) (Prerequisite: The application should be running on port number : 8003)
![Resource](images/accountresource.png)

## Accessing H2 Database
 - [Database URL](http://<hostname>:8003/h2)  (Prerequisite: The application should be running on port number : 8003)

## Initial Data
 - On startup application will load initial data. You can Add/Modify existing data in src/main/resources/data.sql
 - To create current account branch,account type of type current account, customer mapped with branch is mandatory. Initial data has 2 customers with id 1, 2
 
## Achieved Functionalities
   - Create New Account and its transaction
   - Get saved Current account by customer id.
   - While creating new current account it will invoke transaction service to store transaction of that account.
   	 If transaction fails/unable to connect transaction service then created account will be deleted.
   - Circuit breaker pattern is applied to the resource
   - Integration Test using Wiremock (AccountControllerIntegrationTest.java)
   - Unit test 
   - Swagger Documentation
   