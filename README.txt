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

How to run the program:   Paths should be correctly set for the VM. Check the below steps if there is an issue

File/Project Structure/ Libraries:    Set lib to path C:\Users\LabUser\Desktop\javafx-sdk-17.0.1\lib
                                      Set MySQL Connector to C:\Users\LabUser\Desktop\mysql-connector-java-8.025\mysql-connector-java-8.0.25.jar

File/ Settings/ Path Variables:       Set PATH_TO_FX to C:\Users\LabUser\Desktop\javafx-sdk-17.0.1\lib

Ensure JDK 17.0.1 for Java 17 is selected in the Run/Debug Configurations screen for Main

Additional Report: The additional report is reached by clicking the 'Customer Activity by Region' button located
on the Report tab of the Home Screen. The purpose of this report is to show the number of appointments a customer
has had in the past and scheduled in the future. The customers data is separated by country.

LAMBDAS function locations (by module) in the program:

	AddCustomer
		initialize();

    DAOInterface
    		customerToString();

	ModifyCustomer
		initialize();


