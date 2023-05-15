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
• Java 11;
• CDI (Context and Dependency Injection) 1.2;
• EJB (Enterprise JavaBeans) 3.2;
• JSF (Java Server Faces) w wersji 2.3;
• JTA (Java Transaction API) 1.2;
• BootsFaces 1.5.0;
• Project Lombok 1.18.20;
• JPA (Java Persistence API) 2.1;

### Development environment
• NetBeans IDE 12;
• Maven 3.6;
• Java Enterprise Edition 7.0;
• Modelio Open Source 4.1

### Runtime
• Payara 5.183;
• Java DB (Apache Derby)10.14.2.0;
