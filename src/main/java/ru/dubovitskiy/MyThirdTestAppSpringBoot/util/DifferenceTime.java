package ru.dubovitskiy.MyThirdTestAppSpringBoot.util;

import ru.dubovitskiy.MyThirdTestAppSpringBoot.model.Request;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DifferenceTime {


    public static String differenceCalculate (Request request) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date date1 = dateFormat.parse(request.getSystemTime());
        Date date2 = dateFormat.parse(DataTimeUtil.getCustomFormat().format(new Date()));

        long timeDifferenceInMillis = date2.getTime() - date1.getTime();
        SimpleDateFormat resultFormat = new SimpleDateFormat("ss.SSS");
        String timeDifferenceFormatted = resultFormat.format(new Date(timeDifferenceInMillis));


        return timeDifferenceFormatted;
    }
}
