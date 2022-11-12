package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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





public class ReportOne implements Initializable {
    public AnchorPane anchorpaneFX;
    public TableColumn monthFX;
    public TableColumn typeFX;
    public Button closeFX;
    public TableColumn totalFX;
    public TableView tableFX;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentDao.monthTypeList.clear();
        try {
            AppointmentDao.setMonthTypeList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        tableFX.setItems(AppointmentDao.getMonthTypeList());
        monthFX.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeFX.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalFX.setCellValueFactory(new PropertyValueFactory<>("total"));



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
