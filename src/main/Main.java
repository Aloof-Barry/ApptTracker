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

