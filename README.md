# Safety Assessement App
PROJECT ENDING POSTGRADUATE STUDIES "MODERN BUSINESS APPLICATIONS JAVA/JAKART EE"

## Introduction
The aim of the final project was to create a multi-tenant IT system
supporting creation of reports on the safety of cosmetic products. Cosmetic product assessement 
process would start with the Sales introducing product for evaluation. Each cosmetic has assigned an order number, but one order
may include several cosmetics to evaluate. Every cosmetic entry at the moment
of creation must have an unique name, assigned category and specified
composition (list of raw materials separated with a comma). 
System automatically assigns the required analyzes to the cosmetic basing on category,
and creates toxicological description basing on the composition.Safety Assessor can accept the cosmetic for 
assessement or resign from previously chosen one. Final peparation of safety report in predesigned form 
and sending it to client takes place outside of this application.

The IT system consists of statefull web application prepared in Java Enterprise Edition technology and relational database.
It was prepared with three-layer model that allowed to separate layers of view, buisness logic and data storage.
All application sites have unified graphical user interface. The development environment was the NetBeans IDE, the deployment server is
Payara, JavaDB was selected as a database management system.

The functionalities provided to the user vary depending on the access level assigned to the user account.
To be able to use the application, the user must have a modern web browser and have
an individual account.  It is possible to operate the user interface in the Polish or English language version
depending on the language preferences set in the browser. 

System allows simultaneous access to data by many authenticated users,
thanks to the use of OLTP (On-Line Transactional Processing) and optimistic locks.

## Used tools and technologies with version

### Technological stack
* Java 11;
* CDI (Context and Dependency Injection) 1.2;
* EJB (Enterprise JavaBeans) 3.2;
* JSF (Java Server Faces) w wersji 2.3;
* JTA (Java Transaction API) 1.2;
* BootsFaces 1.5.0;
* Project Lombok 1.18.20;
* JPA (Java Persistence API) 2.1;

### Development environment
* NetBeans IDE 12;
* Maven 3.6;
* Java Enterprise Edition 7.0;
* Modelio Open Source 4.1

### Runtime
* Payara 5.183;
* Java DB (Apache Derby)10.14.2.0;

## Requirenments

### Functional
Application has five access levels Guest, Administrator, Sales, Laboratory and Safety Assessor. 
Every account has exactly one access level. Each of the access levels is assigned other collection of use cases.
Authentication is done through credentials: login and password.

### Non-functional
System meets following non-functional requirenments:
* application is a monolith in three-layer architecture with view layer, business logic layer and data storage layer,
* system requires authentication with credentials: login and password,
* the authentication patterns are stored in a relational database, 
* passwords are stored in the form of hashes calculated with the SHA-256 algorithm,
* unique logins for user accounts are required,
* business data of application are stored in relational database,
* user can use account if it has assigned access level, is confirmed and active,
* the relational model is mapped using the JPA mapping standard,
* data consistency in the system is ensured by transactions and optimistic locks,
* it provides a mechanism for reporting messages about events and collecting them in the event log,
* the user interface is provided by dynamically generated WWW pages accessible through a modern web browser,
* it ensures the internationalization of the user interface and reported messages. Available languages are Polish and English.

## Deployment instructions

Launching the created system requires proper preparation of the environment,
which has a working operating system and a modern web browser.
In addition, it must have the Payara application server version 5.183 installed and the system
JavaDB (Apache Derby) version 10.14.2.0. Payara application server and system JavaDB database management 
should be started and then you should follow the steps described in the following subsections.

### Database creation
The database should be created with the help of the utility
ij that comes with Apache Derby. The database configuration has been defined
in the glassfish-resources.xml file.

In the command line, start the database server according to the instructions shown below:
* java -jar $DERBY_HOME/lib/derbyrun.jar server start

Then, in the second window, run the ij utility and create the database data:
* java -jar $DERBY_HOME/lib/derbyrun.jar ij
* ij>CONNECT ‘jdbc:derby://localhost1527//SafetyAssessementDB; create=true; user = safety; password = safety‘;
* ij>exit;

### Configuration of the security area on the server
Open your web browser and enter the URL of your local server in the address bar
http://localhost:48/common/index.jsf. Please select from the menu on the left
Configurations → server-config → Security → Realms. Then select the New button on the right
page. Complete the opened form with the data provided below and press OK.
* Ream Name SafetyAssessementJDBCRealm
* Class Name: com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm
* JAAS Context: jdbcRealm
* JNDI: jdbc/SafetyAssessementDS
* User Table: ACCOUNTS
* User Name Column: LOGIN
* Password Column: PASSWORD
* Group Table: ACCOUNTS
* Group Name Column: TYPE

### Uploading the application to the application server and launching it
Staying on the page http://localhost:48/common/index.jsf from the menu on the left, select
the Applications button, and then the Deploy button:
![image](https://github.com/AleksandraKemona/SafetyAssessement/assets/87969897/727682fb-21d3-4ae7-a58b-a90ebf8d2d65)
By using the Browse button, select the SafetyAssessement-1.0.war file, wchich is available in SafetyAssesement folder.
![image](https://github.com/AleksandraKemona/SafetyAssessement/assets/87969897/fa94b603-2483-429b-9c0d-adc337634428)

The path SafetyAssessement/target/SafetyAssessement-1.0.war leads to the file. After attaching the file
confirm the selection with the OK button. The application should be visible on the server. To run it
select the Launch button from the Action column:
![image](https://github.com/AleksandraKemona/SafetyAssessement/assets/87969897/54a138f2-6fc8-4682-959f-5d4c2361d3b9)

### Placing database structures and initialization data in the database
In order for the user to be able to use the functionality of the system, placing initialization data
data containing e.g. user accounts is required. To do this, load the dbStartingData.sql file
according to the instruction presented in the listing:
* ij>run ‘folder_name/SafetyAssessement/src/main/resources/dbStartingData.sql
The deployed application is available at http://localhost:8080/SafetyAssessement.
If all the above steps have been followed correctly, you will be able to log in
to the application using one of the predefined accounts. Logins and passwords for these accounts are included in table below:
![image](https://github.com/AleksandraKemona/SafetyAssessement/assets/87969897/eccbccfd-ff5c-4ca7-99ff-8d802c3316e0)






