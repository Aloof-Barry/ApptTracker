Appointment Scheduling Application

Purpose:	The purpose of this application is to provide a user interface that users can add, update, and delete both
customers and appointments from the client's database. The application can also generate reports and log sign-in attempts.

Author: Cody Crusenberry
Email: ccruse7@wgu.edu
Student ID: 001418316
Application Version 1.0
Date 11/26/2022

IDE: IntelliJ IDEA Community Edition 2022.2.1
Java SE JDK-18.0.2.1
JavaFX SDK 18.0.1
MySQL-connector-java-8.0.29

How to run the program: press run after JDK, JavaFX library, and MySQL connector are pathed to the program

Additional Report: The additional report is reached by clicking the 'Customer Schedule' button found in the 
'Reports' tab of the Home Screen. The report provides a choice box to select a customer. After selecting a 
customer, the table will populate with all of that customer's appointments in the database. 

LAMDAS function locations (by module) in the program:

	AddCustomer
		initialize();

	ModifyCustomer
		initialize();

	DAOInterface
		customerToString();
