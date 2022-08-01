package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.AppointmentDao;
import model.CustomerDao;
import model.Logger;
import model.Report;


public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception{
        // Init Singletons
        Logger.getLogger();
        Report.getReport();
        AppointmentDao.getAppointmentDao();
        CustomerDao.getCustomerDao();

        // Init LoginScreen
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}

// Adding comment to see if github is working