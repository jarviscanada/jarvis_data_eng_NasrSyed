# Introduction

The JDBC application developed primarily in Java deals with an RDBMS database and data manipulation of this data through OOP concepts such as Data Access Object (DAO), Data Transfer Object (DTO), and various interfaces to conjoin both the Java programming side and the relational database side of things. At the core of this Java application, are the abilities to perform create, read, update, and delete (CRUD) operations from a RDBMS created in Dockerized container of a PSQL instance. 
Apart from the main Java implementation of the project, Maven, Docker, PostgreSQL, and a command line interface are all interconnected to produce this application. As evident in the name itself, the heart of this project lies at the concept of the JDBC API which allows Java programs to access database management systems, and thus perform database work from a Java program interface.

## The project entails the following core junctions:
- A Java application (using Java 8) hosts the primary tools of this data extraction system, which is packaged and managed by Maven. 
- A PostgreSQL JDBC driver is also installed in this Maven package. 
- A relational database is created via PostgreSQL and containerized in Docker, via the command line interface.
- The Java application contains many classes and methods which allow for the basic CRUD operations to be performed against the database.
 
### Technologies Used:
* Java
* JDBC
* PostgreSQL
* PSQL Client
* Maven
* IntelliJ IDEA
* Docker


# Implementation
## ER Diagram
ER diagram

## Design Patterns
There are 2 common patterns that can be used when implementing the Data Access Layer between a software program and database, the DAO pattern and the Repository Design pattern. This project specifically, uses the DAO pattern.

### DAO Design Pattern
The Data Access Object is the heart of this project, defining the interface between the Java software application and the relational database. It is simply a class which enables the CRUD actions to be performed on an object in relation to a database. This is all done by way of an abstract API. In particular with regards to this project, a "CustomerDAO" class was implemented to enable these operations. CRUD operations were performed in main JDBC executor class. 
If a DAO is to be used, then by default a Data Transfer Object (DTO) is also to be implemented. It provides a single domain of data, and fully encapsulates objects. The input/output of a single DAO is a single DTO. Simply put in summary, a DTO is used to transfer data between the modules of the application.

### Repository Design Pattern
THe repository design pattern is another way of dealing with data access logic. It is defined by single-table access per class. Instead of database joining, you join in code. 

# Test
How you test your app against the database? (e.g. database setup, test data set up, query result)
The program was tested against a running Dockerized PSQL instance by calling upon the main method to retrieve basic CRUD data based on the methods developed. 