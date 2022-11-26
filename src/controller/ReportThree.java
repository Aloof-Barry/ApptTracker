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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Controller Class for the Report 3 Screen
 */
public class ReportThree implements Initializable {

    /***
     * JavaFX object to represent the anchor pane on the user interface
     */
    public AnchorPane anchorpaneFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public ChoiceBox customerFX;

    /***
     * JavaFX object to represent the table on the user interface
     */
    public TableView tableFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn appointmentidFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn titleFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn typeFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn descriptionFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn startFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn endFX;

    /***
     * JavaFX object to represent fields on the user interface
     */
    public TableColumn contactFX;

    /***
     * Populates the items in the table
     * Lambda expression is set to trigger when the Customer choice box is changed. The function populates the table with the chosen customer's information
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            customerFX.setItems(AppointmentDao.selectCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        customerFX.setOnAction(event -> {
            ObservableList appointmentList = null;
            try {
                appointmentList = AppointmentDao.customerSchedule((customerFX.getValue()).toString());
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
            contactFX.setCellValueFactory(new PropertyValueFactory<>("contact"));
        });
    }

    /***
     * Method for going to the Home Screen
     * @throws IOException Failed I/O operation
     */
    public void toHome() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/HomeScreen.fxml")));
        Stage stage = (Stage) (anchorpaneFX).getScene().getWindow();
        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }

    /***
     * Close button
     * @param actionEvent passed when closed button is pressed on the screen
     * @throws IOException Failed I/O operation
     */
    public void onClose(ActionEvent actionEvent) throws IOException {
        toHome();
    }
}
