package helper;

import java.sql.Connection;
import java.sql.DriverManager;

/***
 * Sets up the connection with the MySQL server
 */
public abstract class JDBC {

    /***
     * String to be concatenated into jdbcUrl
     */
    private static final String prot = "jdbc";

    /***
     * String to be concatenated into jdbcUrl
     */
    private static final String ven = ":mysql:";

    /***
     * String to be concatenated into jdbcUrl
     */
    private static final String loc = "//localhost/";

    /***
     * String to be concatenated into jdbcUrl
     */
    private static final String dbName = "client_schedule";

    /***
     * Variable to hold the JDBC URL
     */
    private static final String jdbcUrl = prot + ven + loc + dbName;

    /***
     * Variable to hold the driver reference
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    /***
     * User name for the database
     */
    private static final String user = "sqlUser";

    /***
     * database password
     */
    private static String pass = "Passw0rd!";

    /***
     * Represents the connection interface
     */
    public static Connection connection;

    /***
     * Returns the connection
     * @return
     */
    public static Connection getConnection(){
        return connection;
    }

    /***
     * Connects to the database
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, user, pass);
            System.out.println("Connection complete");
        }
        catch(Exception e)
        {
            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /***
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection Ended");
        }
        catch(Exception e)
        {
            System.out.println("Connection Closing Error: " + e.getMessage());
        }
    }
}
