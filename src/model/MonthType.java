package model;

public class MonthType {

    String month;
    String type;
    int total;

    public MonthType(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        switch(Integer.parseInt(month)){
            case 1:
                this.month = "January";
                break;
            case 2:
                this.month = "February";
                break;
            case 3:
                this.month = "March";
                break;
            case 4:
                this.month = "April";
                break;
            case 5:
                this.month = "May";
                break;
            case 6:
                this.month = "June";
                break;
            case 7:
                this.month = "July";
                break;
            case 8:
                this.month = "August";
                break;
            case 9:
                this.month = "September";
                break;
            case 10:
                this.month = "October";
                break;
            case 11:
                this.month = "November";
                break;
            case 12:
                this.month = "December";
                break;

        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
