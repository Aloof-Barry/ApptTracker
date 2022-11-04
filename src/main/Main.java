package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AppointmentDao;
import model.CustomerDao;
import model.Logger;
import model.Report;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/***
 * Main Class of Program
 */
public class Main extends Application{

    /***
     * Start method
     * Initiates all Singleton class instances
     * Loads the LoginScreen controller with ResourceBundle
     * @param stage The initial stage to be set
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{

        JDBC.openConnection();

        // Init Singletons
        Logger.getLogger();
        Report.getReport();
        AppointmentDao.getAppointmentDao();
        CustomerDao.getCustomerDao();

        // Test the French internat.
        //      Locale myLocale = Locale.FRENCH;

        // assign the correct ResourceBundle

        Locale myLocale = Locale.getDefault();
        ResourceBundle rBundle = ResourceBundle.getBundle("helper.lang", myLocale);

        // Init LoginScreen

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"), rBundle);
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();


        //Testing


        //JDBC.closeConnection();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

// Adding comment to see if github is working