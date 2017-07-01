package bean;

/**
 * Created by Administrator on 2017-6-30.
 */
public class WorkingRate {

    private String pr_name;
    private double totalWorkHours;
    private int vehiclecount;
    private double rate;

    public WorkingRate(String pr_name, double totalWorkHours, int vehiclecount, double rate) {
        this.pr_name = pr_name;
        this.totalWorkHours = totalWorkHours;
        this.vehiclecount = vehiclecount;
        this.rate = rate;
    }

    public String getPr_name() {
        return pr_name;
    }

    public void setPr_name(String pr_name) {
        this.pr_name = pr_name;
    }

    public double getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(double totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public int getVehiclecount() {
        return vehiclecount;
    }

    public void setVehiclecount(int vehiclecount) {
        this.vehiclecount = vehiclecount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
