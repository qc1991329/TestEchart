package bean;

/**
 * Created by Administrator on 2017-7-18.
 */
public class VehModelOverload {

    private String modelName;
    private double hours;
    private int vehCount;

    public VehModelOverload(String modelName, double hours, int vehCount) {
        this.modelName = modelName;
        this.hours = hours;
        this.vehCount = vehCount;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getVehCount() {
        return vehCount;
    }

    public void setVehCount(int vehCount) {
        this.vehCount = vehCount;
    }
}
