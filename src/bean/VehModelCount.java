package bean;

/**
 * Created by Administrator on 2017-7-1.
 */
public class VehModelCount {

    private  String modelName;
    private  int salCount;

    public VehModelCount(String modelName, int salCount) {
        this.modelName = modelName;
        this.salCount = salCount;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getSalCount() {
        return salCount;
    }

    public void setSalCount(int salCount) {
        this.salCount = salCount;
    }
}
