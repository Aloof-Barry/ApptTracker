package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

public class Logger {

    private static final Logger logger = new Logger();

    private Logger(){

    }

    public static Logger getLogger(){
        return logger;
    }

    public static int currentUser;





    public static boolean signIn(String username, String password) throws SQLException {
        String sql = "SELECT User_ID FROM client_schedule.users WHERE User_Name =? AND Password =?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        ObservableList<Integer> userList = FXCollections.observableArrayList();
        while(rs.next()){
            int user =  rs.getInt("User_ID");
            userList.add(user);
        }
        if(userList.isEmpty()){
            return true;
        } else {
            currentUser = userList.get(0);
            return false;
        }
    }

    public static void userFifteen() throws SQLException {
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                "FROM client_schedule.appointments AS a\n" +
                "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                "WHERE User_ID =? AND Start >= NOW() AND Start <= NOW() + INTERVAL 15 MINUTE\n" +
                "ORDER BY Start";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(currentUser));
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
            int userId = rs.getInt("User_ID");
            int customerID = rs.getInt("Customer_ID");

            LocalDateTime start = Checker.utcToLocal(startTS.toLocalDateTime());
            LocalDateTime end = Checker.utcToLocal(endTS.toLocalDateTime());

            Appointment appointment = new Appointment(appointmentID, title, description, location, contact, type,
                    start, end, customerID, userId);
            schedule.add(appointment);
        }
        if(schedule.isEmpty()){
            Checker.alertFifteen(false, schedule);
        } else {Checker.alertFifteen(true, schedule);}




    }




}
