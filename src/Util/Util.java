package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017-7-19.
 */
public class Util {

    public static String dateToStr(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);
        return dateString;
    }
}
