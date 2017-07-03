package bean;

/**
 * Created by Administrator on 2016-12-13.
 */
public class VehWorkingRate {

    private String province;
    private  double workingRate;

    public VehWorkingRate(String province, double workingRate) {
        this.province = province;
        this.workingRate = workingRate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getWorkingRate() {
        return workingRate;
    }

    public void setWorkingRate(double workingRate) {
        this.workingRate = workingRate;
    }
}
