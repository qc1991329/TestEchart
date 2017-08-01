package bean;

/**
 * Created by Administrator on 2017-8-1.
 */
public class CrmMonthPayment {
    String day;
    double payment;

    public CrmMonthPayment(String day, double payment) {
        this.day = day;
        this.payment = payment;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
