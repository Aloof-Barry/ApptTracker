package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/***
 * Singleton Class with the responsibility of managing user logins
 */
public class Logger {

    /***
     * Single instance of Logger Class
     */
    private static final Logger logger = new Logger();

    /***
     * Constructor
     */
    private Logger(){
    }

    /***
     * Method to create singleton
     * @return 1 instance of the Logger class
     */
    public static Logger getLogger(){
        return logger;
    }

    /***
     * Class member field to hold the current user's user id
     */
    public static int currentUser;

    /***
     * Attempts to sign in the user with given username and password. if the correct username and password is entered they are
     * allowed to go to the home screen
     * @param username username
     * @param password password
     * @return true if the user fails sign in attempt, false if user signs in successfully
     * @throws SQLException database error from query
     * @throws IOException as a result of the makeLog method call
     */
    public static boolean signIn(String username, String password) throws SQLException, IOException {
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
            makeLog("No User Name Given", false);
            return true;
        } else {
            currentUser = userList.get(0);
            makeLog(username, true);
            return false;
        }
    }

    /***
     * Checks for appointments involving the user that start within 15 minutes of login.
     * @throws SQLException Database error
     */
    public static void userFifteen() throws SQLException {
        Timestamp now = Timestamp.valueOf(Checker.localToUTC(LocalDateTime.now()));
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name, a.Type, a.Start, a.End, a.User_ID, a.Customer_ID\n" +
                "FROM client_schedule.appointments AS a\n" +
                "JOIN client_schedule.contacts AS c ON a.Contact_ID = c.Contact_ID\n" +
                "WHERE User_ID =? AND Start >=? AND Start <= (?+ INTERVAL 15 MINUTE)\n" +
                "ORDER BY Start";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, Integer.toString(currentUser));
        ps.setString(2, now.toString());
        ps.setString(3, now.toString());

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

            System.out.println(appointment.getAppointmentId() + " , start: " + appointment.getStart() + " , userid " + appointment.getUserId());

            schedule.add(appointment);
        }
        if(schedule.isEmpty()){
            System.out.println("Schedule is empty");
            Checker.alertFifteen(false, schedule);

        } else {Checker.alertFifteen(true, schedule);}
    }

    /***
     * Logs user sign in activity to login_activity.txt located in the application root folder
     * @param username User's username
     * @param success Boolean: true if login attempt was successful, false if login attempt was unsuccessful
     * @throws IOException if the FileWriter method cannot find the file it is looking for
     */
    private static void makeLog(String username, Boolean success) throws IOException {
        FileWriter writer = new FileWriter("login_activity.txt", true);
        String time = Checker.myTimeFormat(Checker.localToUTC(LocalDateTime.now()));
        String text = username + " , Successful Login: " + success + " , " + time + " UTC \n";

        for (int i=0; i < text.length(); i++){
            writer.write(text.charAt(i));
        }
        writer.close();
        System.out.println("Logged : " + text);
    }
}
