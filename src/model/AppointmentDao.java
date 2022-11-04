package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppointmentDao {

    /***
     * Single instance of AppointmentDao class
     */
    private static final AppointmentDao appointmentDao = new AppointmentDao();


    /***
     * Constructor
     */
    private AppointmentDao(){

    }

    /***
     * Function to create singleton
     * @return 1 instance of the AppointmentDao class
     */
    public static AppointmentDao getAppointmentDao(){
        return appointmentDao;
    }

    public static final List<String> listAllTimes = Arrays.asList("01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
            "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");

    public static final ObservableList<String> allTimes = FXCollections.observableArrayList(listAllTimes);

    public static List getAllTimes(){
        return allTimes;
    }


    /***
     * List to hold every appointment in the database program side
     */
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    public static void addAppointment(Appointment appointment){
        allAppointments.add(appointment);
    }

    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    public static void setAllAppointments(){
        try{
            allAppointments.clear();
            String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                    "FROM client_schedule.appointments AS a\n" +
                    "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title =  rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp startTS = rs.getTimestamp("Start");
                Timestamp endTS = rs.getTimestamp("End");
                int userID = rs.getInt("User_ID");
                int customerID = rs.getInt("Customer_ID");

                LocalDateTime start = startTS.toLocalDateTime();
                LocalDateTime end = endTS.toLocalDateTime();

                Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type,
                        start, end, customerID, userID);
                addAppointment(appointment);
                System.out.println(appointment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static void insertAppointment(Appointment appointment){

        try{

            String sql = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type," +
                    " Start, End, Customer_ID, User_ID, Contact_ID)\n" +
                    "VALUES (null,\'" + appointment.getTitle() + "\',\'" + appointment.getDescription() + "\',\'"
                    + appointment.getLocation() + "\',\'" + appointment.getType() + "\',\'" + Timestamp.valueOf(appointment.getStart()) + "\',\'" + Timestamp.valueOf(appointment.getEnd()) + "\'," +
                    appointment.getCustomerId() + "," + appointment.getUserId() + "," + contactToID(appointment.getContact()) + ")";
            System.out.println(sql);

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.execute();
            System.out.println("Insert Appointment Ran");
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static int contactToID(String contact) throws SQLException {
        String sql = "SELECT Contact_ID FROM client_schedule.contacts WHERE Contact_Name =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contact);
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> contactIDList = FXCollections.observableArrayList();
        while(rs.next()){
            int conID =  rs.getInt("Contact_ID");

            contactIDList.add(conID);
        }
        return contactIDList.get(0);
    }



    public static ObservableList selectContacts() throws SQLException {

        ObservableList<String> contactList = FXCollections.observableArrayList();

        String sql = "SELECT Contact_Name\n" +
                "FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String contact =  rs.getString("Contact_Name");
            System.out.println("append " + contact + " to list");
            contactList.add(contact);
        }

        return contactList;
    }

    public static ObservableList selectCustomerID() throws SQLException {

        ObservableList<String> customerList = FXCollections.observableArrayList();

        String sql = "SELECT Customer_ID\n" +
                "FROM client_schedule.customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String customer =  rs.getString("Customer_ID");
            System.out.println("append " + customer + " to list");
            customerList.add(customer);
        }

        return customerList;
    }

    public static ObservableList selectUserID() throws SQLException {

        ObservableList<String> userList = FXCollections.observableArrayList();

        String sql = "SELECT User_ID\n" +
                "FROM client_schedule.users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String user =  rs.getString("User_ID");
            System.out.println("append " + user + " to list");
            userList.add(user);
        }

        return userList;
    }
}


