package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/***
 * Checker is a singleton class used for input validation and time conversions. Contains static methods for the controllers to use
 */
public class Checker {
    /***
     * Creates the Singleton class instance
     */
    private static final Checker checker = new Checker();

    /***
     * Constructor
     */
    private Checker(){

    }

    /***
     * Gets the class instance
     * @return final checker
     */
    public static Checker getChecker(){

        return checker;
    }

    /***
     * Checks if a given customer has any appointments in the databse
     * @param customer the customer to be verified
     * @return true if appointments exist, false if the customer has no appointments
     * @throws SQLException
     */
    public static boolean checkCustKeyRes(Customer customer) throws SQLException {
        String sql = "SELECT Appointment_ID FROM client_schedule.appointments WHERE Customer_ID =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(customer.getCustomerId()));
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> apptIDList = FXCollections.observableArrayList();
        if (rs.next()){
            return true;
        } else {return false;}

    }

    /***
     * Displays JavaFX alert
     */
    public static void deleteCustNotification(){
        Alert errorA = new Alert(Alert.AlertType.INFORMATION);
        errorA.setTitle("Notification");
        errorA.setHeaderText("Customer Deleted");
        errorA.setContentText("The customer and all related appointments have been deleted");
        errorA.showAndWait();
        return;
    }

    /***
     * Displays JavaFX alert
     */
    public static void deleteApptNotification(Appointment appointment){
        Alert errorA = new Alert(Alert.AlertType.INFORMATION);
        errorA.setTitle("Notification");
        errorA.setHeaderText("Appointment Cancelled");
        errorA.setContentText("Appointment ID: " + appointment.getAppointmentId() + "\n" +
                "Type: " + appointment.getType());
        errorA.showAndWait();
        return;
    }

    /***
     * Converts local time to UTC time
     * @param t local time as a LocalDateTime
     * @return UTC time as a LocalDateTime
     */
    public static LocalDateTime localToUTC(LocalDateTime t){
        ZonedDateTime zoned = t.atZone(ZoneId.systemDefault());
        ZonedDateTime utcZoned = zoned.withZoneSameInstant(ZoneOffset.UTC);
        LocalDateTime utcLDT = utcZoned.toLocalDateTime();
        return utcLDT;
    }

    /***
     * Converts UTC time to local time
     * @param t UTC time as a LocalDateTime
     * @return local time as a LocalDateTime
     */
    public static LocalDateTime utcToLocal(LocalDateTime t){
        ZonedDateTime zoned = t.atZone(ZoneId.of("UTC"));
        ZonedDateTime localZoned = zoned.withZoneSameInstant(ZoneOffset.systemDefault());
        LocalDateTime localLDT = localZoned.toLocalDateTime();
        return localLDT;
    }

    /***
     * Checks for empty fields on the customer screens
     * @param customer Customer object
     * @return true if fields are empty, false if fields are not empty
     */
    public static boolean customerInputVal(Customer customer) throws SQLException {
        if(customer.getName().isEmpty()
                || customer.getAddress().isEmpty()
                || customer.getPhone().isEmpty()
                || customer.getPostalCode().isEmpty()
                || customer.getDivision().isBlank()
                || customer.getCountry().isBlank()
        ) {Alert errorA = new Alert(Alert.AlertType.ERROR);
            errorA.setTitle("ERROR");
            errorA.setHeaderText("Input Validation Error");
            errorA.setContentText("Name, address, postal code, and phone fields can not be blank");
            errorA.showAndWait();

            return true;} else {return false;}
    }

    /***
     * Validates  that appointment is scheduled within business hours
     * @param appointment Appointment object
     * @return true if appointment is not within business hours
     */
    public static boolean workDays(Appointment appointment){
        LocalTime morning = LocalTime.of(8,0);
        LocalTime evening = LocalTime.of(22,0);
        ZonedDateTime startZoned = appointment.getStart().atZone(ZoneId.systemDefault());
        System.out.println("start local zone date time: " + startZoned.toString());
        ZonedDateTime startESTZoned = startZoned.withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println("start EST zone date time: " + startESTZoned.toString());
        LocalTime start = startESTZoned.toLocalTime();
        System.out.println("start EST:" + start);

        ZonedDateTime endZoned = appointment.getEnd().atZone(ZoneId.systemDefault());
        System.out.println("end local zone date time: " + endZoned.toString());
        ZonedDateTime endESTZoned = endZoned.withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println("end EST zone date time: " + endESTZoned);
        LocalTime end = endESTZoned.toLocalTime();
        System.out.println("end EST: " + end.toString());

        LocalDate startDay = startESTZoned.toLocalDate();
        System.out.println("Start Day: "+ startDay.toString());
        LocalDate endDay = endESTZoned.toLocalDate();
        System.out.println("End Day: " + endDay.toString());

        System.out.println("TESTS" + start.isBefore(morning) + start.isAfter(evening) + "\n" +
                end.isBefore(morning) + end.isAfter(evening) + "\n" +
                startDay.isEqual(endDay));

        if (start.isBefore(morning)
        || start.isAfter(evening)
        || end.isBefore(morning)
        || end.isAfter(evening)
        || (!startDay.isEqual(endDay))){
            Alert errorA = new Alert(Alert.AlertType.ERROR);
            errorA.setTitle("ERROR");
            errorA.setHeaderText("Input Validation Error");
            errorA.setContentText("Appointments must be scheduled within business hours 8:00 AM to 10:00 PM.");
            errorA.showAndWait();
            return true;
        } else {return false;}
    }

    /***
     * Checks for overlapping appointments for a customer
     * @param appointment Appointment object
     * @return true if a customer has overlapping appointments
     * @throws SQLException database error
     */
    public static boolean overlap(Appointment appointment) throws SQLException {
        int customerID = appointment.getCustomerId();
        ObservableList<LocalDateTime> starts = FXCollections.observableArrayList();
        ObservableList<LocalDateTime> ends = FXCollections.observableArrayList();

        String sql = "SELECT Start, End\n" +
                "FROM client_schedule.appointments\n"+
                "WHERE Customer_ID = " + customerID + "\n"+
                "AND Appointment_ID != " + appointment.getAppointmentId();
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            LocalDateTime start =  rs.getTimestamp("Start").toLocalDateTime();
            starts.add(utcToLocal(start));
            LocalDateTime end =  rs.getTimestamp("End").toLocalDateTime();
            ends.add(utcToLocal(end));
        }

        /*
        System.out.println("-------------------\n" +
                "Overlap Debug\n" +
                "--------------------\n" +
                "Start Times : " + appointment.getStart().toLocalTime() + "   " + starts.get(0).toLocalTime() +"\n" +
                "End Times : " + appointment.getEnd().toLocalTime() + "   " + ends.get(0).toLocalTime());
        */

        if(starts.size() < 1){
            return false;
        }
        if(appointment.getStart().isAfter(appointment.getEnd())){
            Alert errorB = new Alert(Alert.AlertType.ERROR);
            errorB.setTitle("ERROR");
            errorB.setHeaderText("Input Validation Error");
            errorB.setContentText("Appointment ends before it starts.");
            errorB.showAndWait();
            return true;
        }
        for (int i = 0; i < starts.size(); i++ ){
            if(appointment.getStart().isAfter(starts.get(i)) && appointment.getStart().isBefore(ends.get(i))
            || appointment.getEnd().isAfter(starts.get(i)) && appointment.getEnd().isBefore(ends.get(i))
            || appointment.getStart().isEqual(starts.get(i))
            || appointment.getEnd().isEqual(ends.get(i))
            || starts.get(i).isAfter(appointment.getStart()) && starts.get(i).isBefore(appointment.getEnd())){

                Alert errorA = new Alert(Alert.AlertType.ERROR);
                errorA.setTitle("ERROR");
                errorA.setHeaderText("Input Validation Error");
                errorA.setContentText("Customer appointments must not overlap.");
                errorA.showAndWait();
                return true;
        } else {continue;}
        }
        return false;
    }

    /***
     * Displays a notification for any appointments scheduled within 15 minutes
     * @param val a boolean value. True for an appointment being within 15 minutes
     * @param schedule list of all appointments for a contact
     */
    public static void alertFifteen(Boolean val, ObservableList<Appointment> schedule){
        if (val){
            System.out.println("alert15 True");
            Appointment appointment = schedule.get(0);
            Alert errorA = new Alert(Alert.AlertType.INFORMATION);
            errorA.setTitle("Welcome");
            errorA.setHeaderText("Appointment Scheduled Within 15 Minutes");
            errorA.setContentText("Appointment ID: " + appointment.getAppointmentId() + "\n Start: " + AppointmentDao.myFormat(appointment.getStart()));
            errorA.showAndWait();

        } else {
            System.out.println("alert15 False");
            Alert errorB = new Alert(Alert.AlertType.INFORMATION);
            errorB.setTitle("Welcome");
            errorB.setHeaderText("No Appointments in the Next 15 Minutes");
            errorB.setContentText("");
            errorB.showAndWait();
        }
    }

    /***
     * Fromats time into a human readable format
     * @param ldt a LocalDateTime to be formatted
     * @return Human readable time as a String
     */
    public static String myTimeFormat(LocalDateTime ldt){
        String easyDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(ldt);
        return easyDateTime;
    }


    public static boolean apptInputVal(String title, String description, String location, String type, String contact,
                                       int customerID, LocalTime startTime, LocalTime endTime, int userID, LocalDate startDate,
                                       LocalDate endDate){

        if (title.isEmpty()
        || description.isEmpty()
        || location.isEmpty()
        || type.isEmpty()
        || contact.isEmpty()
        || (((Integer) customerID) == null)
        || startTime == null
        || endTime == null
        || (((Integer) userID) == null)
        || startDate == null
        || endDate == null){
            apptInputAlert();
            return true;
        } else {return false;}

    }

    public static void apptInputAlert(){
        Alert errorB = new Alert(Alert.AlertType.ERROR);
        errorB.setTitle("ERROR");
        errorB.setHeaderText("Input Validation Error");
        errorB.setContentText("Please fill all fields in the form");
        errorB.showAndWait();

    }




}
