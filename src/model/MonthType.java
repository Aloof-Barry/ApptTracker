package model;

/***
 * Abstract Class to represent appointment types per month. Each instance represents one month
 * and the total number of that appointment type
 */
public class MonthType {

    /***
     * Member field for the month
     */
    String month;

    /***
     * member field for the appointment type
     */
    String type;

    /***
     * member field for the total count of the appointment type for the month
     */
    int total;

    /***
     * Constructor
     * @param month one of twelve months
     * @param type appointment type
     * @param total the sum of all appointments of the same type and month
     */
    public MonthType(String month, String type, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /***
     * getter method
     * @return String month
     */
    public String getMonth() {
        return month;
    }

    /***
     * Setter
     * Converts month (a String containing a number) to a String with the month name
     * @param month month
     */
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

    /***
     * getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /***
     * setter
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /***
     * getter
     * @return total
     */
    public int getTotal() {
        return total;
    }

    /***
     * setter
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
