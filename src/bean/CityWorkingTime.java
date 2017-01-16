package bean;

/**
 * Created by Administrator on 2016-12-13.
 */
public class CityWorkingTime {

    private String city;
    private  double workingtime;

    public CityWorkingTime(String city, double workingtime) {
        this.city = city;
        this.workingtime = workingtime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getWorkingtime() {
        return workingtime;
    }

    public void setWorkingtime(double workingtime) {
        this.workingtime = workingtime;
    }
}
