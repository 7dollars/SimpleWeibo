package com.wmk.wb.utils;

import android.util.Log;

import com.wmk.wb.model.StaticData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by wmk on 2017/4/6.
 */

public class ConvertDate {
    public static Date convert(String datdString) throws ParseException
    {
       // String datdString="Tue Apr 04 11:32:12 +0800 2017";
        datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
        Date dateTrans = format.parse(datdString);
        return dateTrans;
       // Log.e("123123123123",new SimpleDateFormat("yyyy-MM-dd").format(dateTrans));

    }
    public static String calcDate(String date)
    {

        Date system= StaticData.getInstance().getSystem();
        Date now=null;


        try {
            now = convert(date);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }

        String ms=new SimpleDateFormat("hh:mm").format(now);

        system.setMinutes(0);
        system.setSeconds(0);
        system.setHours(0);


        now.setMinutes(0);
        now.setSeconds(0);
        now.setHours(0);


        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(now);
        cal2.setTime(system);
        double dayCount = (cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*3600*24);//从间隔毫秒变成间隔天数
        String ret;
        Log.e("123123","\n相差"+dayCount+"天");
        switch ((int)dayCount)
        {
            case 0:
            {
                ret="今天 "+ms;
                break;
            }
            case 1:
            {
                ret="昨天 "+ms;
                break;
            }
            case 2:
            {
                ret="前天 "+ms;
                break;
            }
            default:
            {
                ret=new SimpleDateFormat("MM月dd日 ").format(now)+ms;
                break;
            }

        }
        return ret;
    }
}
