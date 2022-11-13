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
import java.time.format.DateTimeFormatter;
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

    /***
     * Field
     */
    public static final List<String> listAllTimes = Arrays.asList("01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
            "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00");

    /***
     * Field
     */
    public static final ObservableList<String> allTimes = FXCollections.observableArrayList(listAllTimes);

    /***
     * Field
     */
    public static ObservableList<MonthType> monthTypeList = FXCollections.observableArrayList();

    /***
     * Field
     */
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();




    /***
     * Getter
     * @return static field list allTimes
     */
    public static List getAllTimes(){
        return allTimes;
    }

    /***
     * Getter
     * @return static field allAppointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    /***
     * Method for setter
     * @param appointment is added to the static field list allAppointments
     */
    public static void addAppointment(Appointment appointment){
        allAppointments.add(appointment);
    }

    /***
     * Setter for static field list allAppointments
     * @param i Integer between 0 and 2 that identifies the state of the all, month, and week toggle for appointments table
     * @throws SQLException
     */
    public static void setAllAppointments(int i) throws SQLException {
        try{
            String filter = null;
            switch(i) {
                case 0:
                    filter = "";
                    break;
                case 1:
                    filter = "WHERE month(Start) = month(now())";
                    break;
                case 2:
                    filter = "WHERE week(Start) = week(now())";
                    break;
            }
            allAppointments.clear();
            String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                    "FROM client_schedule.appointments AS a\n" +
                    "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                    filter;
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println(sql);
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

                LocalDateTime start = Checker.utcToLocal(startTS.toLocalDateTime());
                LocalDateTime end = Checker.utcToLocal(endTS.toLocalDateTime());

                Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type,
                        start, end, customerID, userID);
                addAppointment(appointment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObservableList<MonthType> getMonthTypeList(){
        return monthTypeList;
    }

    public static void addMonthType(MonthType monthType){monthTypeList.add(monthType);}

    public static void setMonthTypeList() throws SQLException {
        String  sql = "SELECT MONTH(Start) AS Month, Type, COUNT(Appointment_ID) AS Total\n" +
                "FROM client_schedule.appointments\n" +
                "GROUP BY MONTH(Start), Type\n" +
                "ORDER BY MONTH(Start), Type";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        System.out.println(sql);
        while(rs.next()){
            String month = rs.getString("Month");
            String type =  rs.getString("Type");
            int total = rs.getInt("Total");

            MonthType monthType = new MonthType(month, type, total);
            monthType.setMonth(monthType.getMonth());


            addMonthType(monthType);

        }

    }

    public static void insertAppointment(Appointment appointment){
        try{
            String sql = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type," +
                    " Start, End, Customer_ID, User_ID, Contact_ID)\n" +
                    "VALUES (null,\'" + appointment.getTitle() + "\',\'" + appointment.getDescription() + "\',\'"
                    + appointment.getLocation() + "\',\'" + appointment.getType() + "\',\'" + Timestamp.valueOf(Checker.localToUTC(appointment.getStart())) +
                    "\',\'" + Timestamp.valueOf(Checker.localToUTC(appointment.getEnd())) + "\'," +
                    appointment.getCustomerId() + "," + appointment.getUserId() + "," + contactToID(appointment.getContact()) + ")";
            System.out.println(sql);
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.execute();
            System.out.println(sql);
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

    public static void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE client_schedule.appointments\n" +
                "SET Appointment_ID = " + appointment.getAppointmentId() + ", Title = \'" + appointment.getTitle() +
                "\', Description = \'" + appointment.getDescription() + "\', Location = \'" + appointment.getLocation() +
                "\', Type = \'" + appointment.getType() + "\', Start = \'" + Timestamp.valueOf(Checker.localToUTC(appointment.getStart())) +
                "\', End = \'"+ Timestamp.valueOf(Checker.localToUTC(appointment.getEnd())) + "\', Customer_ID = " + appointment.getCustomerId() +
                ", User_ID = " + appointment.getUserId() + ", Contact_ID = " + contactToID(appointment.getContact()) + "\n" +
                "WHERE Appointment_ID = " + appointment.getAppointmentId();
        System.out.println(sql);


        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.execute();

    }

    public static void deleteAppointment(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments\n" +
                "WHERE Appointment_ID =?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(appointment.getAppointmentId()));
        System.out.println(ps);
        ps.execute();

    }

    public static void deleteCustAppts(Customer customer) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments\n" +
                "WHERE Customer_ID =?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(customer.getCustomerId()));
        System.out.println(ps);
        ps.execute();

    }

    public static ObservableList<Appointment> selectSchedule(String contactChoice) throws SQLException {
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                "FROM client_schedule.appointments AS a\n" +
                "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                "WHERE Contact_Name =? ORDER BY Start";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, contactChoice);
        ResultSet rs = ps.executeQuery();
        System.out.println(sql);

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

            LocalDateTime start = Checker.utcToLocal(startTS.toLocalDateTime());
            LocalDateTime end = Checker.utcToLocal(endTS.toLocalDateTime());

            Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type,
                    start, end, customerID, userID);
            schedule.add(appointment);
        }
        return schedule;
    }

    public static ObservableList selectCustomers() throws SQLException {

        ObservableList<String> customerList = FXCollections.observableArrayList();

        String sql = "SELECT Customer_Name\n" +
                "FROM client_schedule.customers";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String customer =  rs.getString("Customer_Name");
            System.out.println("append " + customer + " to list");
            customerList.add(customer);
        }

        return customerList;
    }

    public static ObservableList<Appointment> customerSchedule(String customer) throws SQLException {
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                "FROM client_schedule.appointments AS a\n" +
                "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +

                "WHERE Customer_ID =? ORDER BY Start";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(customerToID(customer)));
        ResultSet rs = ps.executeQuery();
        System.out.println(sql);

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

            LocalDateTime start = Checker.utcToLocal(startTS.toLocalDateTime());
            LocalDateTime end = Checker.utcToLocal(endTS.toLocalDateTime());

            Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type,
                    start, end, customerID, userID);
            schedule.add(appointment);
        }
        return schedule;
    }

    public static int customerToID(String customer) throws SQLException {
        String sql = "SELECT Customer_ID FROM client_schedule.customers WHERE Customer_Name =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customer);
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
        while(rs.next()){
            int cusID =  rs.getInt("Customer_ID");
            customerIDList.add(cusID);
        }
        return customerIDList.get(0);
    }

    public static String myFormat(LocalDateTime ldt){
        DateTimeFormatter formatStyle = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm");
        return ldt.format(formatStyle);
    }










}


