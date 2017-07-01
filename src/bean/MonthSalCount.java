package bean;

/**
 * Created by Administrator on 2017-7-1.
 */
public class MonthSalCount {

    private String month;
    private int salCount;

    public MonthSalCount(String month, int salCount) {
        this.month = month;
        this.salCount = salCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getSalCount() {
        return salCount;
    }

    public void setSalCount(int salCount) {
        this.salCount = salCount;
    }
}
