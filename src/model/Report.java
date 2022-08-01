package model;

public class Report {
    private static final Report report = new Report();

    private Report(){

    }

    public static Report getReport(){
        return report;
    }
}
