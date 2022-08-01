package model;

public class AppointmentDao {
    private static final AppointmentDao appointmentDao = new AppointmentDao();

    private AppointmentDao(){

    }

    public static AppointmentDao getAppointmentDao(){
        return appointmentDao;
    }
}
