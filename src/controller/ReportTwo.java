package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.AppointmentDao;
import model.CustomerDao;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportTwo implements Initializable {
    public AnchorPane anchorpaneFX;
    public ChoiceBox contactFX;
    public TableView tableFX;
    public TableColumn appointmentidFX;
    public TableColumn titleFX;
    public TableColumn typeFX;
    public TableColumn descriptionFX;
    public TableColumn startFX;
    public TableColumn endFX;
    public TableColumn customeridFX;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactFX.setItems(AppointmentDao.selectContacts());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        contactFX.setOnAction(event -> {
            System.out.println(contactFX.getValue() + " selected from drop down");

            ObservableList appointmentList = null;
            try {
                appointmentList = AppointmentDao.selectSchedule((contactFX.getValue()).toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            tableFX.setItems(appointmentList);

            appointmentidFX.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleFX.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeFX.setCellValueFactory(new PropertyValueFactory<>("type"));
            descriptionFX.setCellValueFactory(new PropertyValueFactory<>("description"));
            startFX.setCellValueFactory(new PropertyValueFactory<>("start"));
            endFX.setCellValueFactory(new PropertyValueFactory<>("end"));
            customeridFX.setCellValueFactory(new PropertyValueFactory<>("customerId"));





        });

    }

    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }


    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
