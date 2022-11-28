package main;

import controller.LoginScreen;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/***
 * Main Class of Program
 * Cody Crusenberry
 * Email: ccruse7@wgu.edu
 * Student ID: 001418316
 * Application Version 1.0
 * Date 11/26/2022
 *
 * IDE: IntelliJ IDEA Community Edition 2022.2.1
 * Java SE JDK-18.0.2.1
 * JavaFX SDK 18.0.1
 * MySQL-connector-java-8.0.29
 */
public class Main extends Application{

    /***
     * Start method
     * Initiates all Singleton class instances
     * Loads the LoginScreen controller with ResourceBundle
     * @param stage The initial stage to be set
     * @throws IOException FXMLoader.load could throw if it does not find the screen it is looking for
     */
    @Override
    public void start(Stage stage) throws IOException {

        JDBC.openConnection();

        // Init Singletons
        Logger.getLogger();
        Report.getReport();
        AppointmentDao.getAppointmentDao();
        CustomerDao.getCustomerDao();
        Checker.getChecker();

        // Test the French internat.
        Locale fra = Locale.FRENCH;

        // assign the correct ResourceBundle

        Locale def = Locale.getDefault();
        ResourceBundle rBundle = ResourceBundle.getBundle("helper.lang", def);
        LoginScreen.rbundle = rBundle;

        // Init LoginScreen

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"), rBundle);
        stage.setTitle(rBundle.getString("signin"));
        stage.setScene(new Scene(root, 750, 500));
        stage.show();

        //Testing

        //JDBC.closeConnection();
    }

    /***
     * Main Method
     * @param args starts the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}

