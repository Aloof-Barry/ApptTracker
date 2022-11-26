package model;

import java.time.LocalDateTime;

/***
 * Abstract class to represent an appointment
 * Includes all the info that we want displayed in the Home Screen. Does not have the same fields as the database table
 */
public class Appointment {

    /***
     * Class member variable
     */
    private int appointmentId;

    /***
     * Class member variable
     */
    private String title;

    /***
     * Class member variable
     */
    private String description;

    /***
     * Class member variable
     */
    private String location;

    /***
     * Class member variable
     */
    private String contact;

    /***
     * Class member variable
     */
    private String type;

    /***
     * Class member variable
     */
    private LocalDateTime start;

    /***
     * Class member variable
     */
    private LocalDateTime end;

    /***
     * Class member variable
     */
    private int customerId;

    /***
     * Class member variable
     */
    private int userId;

    /***
     * Class member variable
     */
    private String formattedStart;

    /***
     * Class member variable
     */
    private String formattedEnd;

    /***
     * Constructor Method
     * @param appointmentId Appointment ID
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param contact Appointment contact
     * @param type Appointment type
     * @param start Appointment start
     * @param end Appointment end
     * @param customerId Appointment customer ID
     * @param userId Appointment user ID
     */
    public Appointment(int appointmentId, String title, String description, String location, String contact, String type,
                       LocalDateTime start, LocalDateTime end, int customerId, int userId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.formattedStart = Checker.myTimeFormat(start);
        this.formattedEnd = Checker.myTimeFormat(end);
    }


    /***
     * Getter Method
     * @return Appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /***
     * Setter Method
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /***
     * Getter Method
     * @return Title
     */
    public String getTitle() {
        return title;
    }

    /***
     * Setter Method
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /***
     * Getter Method
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /***
     * Setter Method
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /***
     * Getter Method
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /***
     * Setter Method
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /***
     * Getter Method
     * @return Contact
     */
    public String getContact() {
        return contact;
    }

    /***
     * Setter Method
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /***
     * Getter Method
     * @return type
     */
    public String getType() {
        return type;
    }

    /***
     * Setter Method
     */
    public void setType(String type) {
        this.type = type;
    }

    /***
     * Getter Method
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }

    /***
     * Setter Method
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
        formattedStart = Checker.myTimeFormat(start);
    }

    /***
     * Getter Method
     * @return end
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /***
     * Setter Method
     */
    public void setEnd(LocalDateTime end) {

        this.end = end;
        formattedEnd = Checker.myTimeFormat(end);
    }

    /***
     * Getter Method
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /***
     * Setter Method
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /***
     * Getter Method
     * @return user ID
     */
    public int getUserId() {
        return userId;
    }

    /***
     * Setter Method
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFormattedStart(){return formattedStart;}

    public String getFormattedEnd(){return formattedEnd;}

}
