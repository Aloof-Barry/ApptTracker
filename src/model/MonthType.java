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
        this.month = month;
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
