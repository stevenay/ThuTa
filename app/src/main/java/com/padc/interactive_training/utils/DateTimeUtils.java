package com.padc.interactive_training.utils;

import android.content.Context;

import com.padc.interactive_training.InteractiveTrainingApp;
import com.padc.interactive_training.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by htoomt on 9/19/2016.
 * email : robinhtoo@gmail.com
 */
public class DateTimeUtils {


    /*
    * => To change from string date in programming language ISO Date format to object date
    * Use one argument method
    *
    * =>To change from string date in any java supported date format to object date
    * Use two arguments method,
    *   - 1st arg for date or date/time format in string
    *   - 2nd arg for date or date/time input in string
    *
    *
    * Contribution for improvement of this utility will be accepted with proper working.
    * Date Formats sample,  "yyyy-MM-dd" , "dd/MM/yyyy", "MM/dd/yyyy", "dd, MMMM, yyyy", "dd, MMM, yyyy"
    * Referenced Java Date Format: https://www.mkyong.com/java/how-to-convert-string-to-date-java/
    * */


    public static Date parseStringToDate(String strDateToParse) {
        return parseStringToDate("yyyy-MM-dd", strDateToParse);
    }

    public static Date parseStringToDateTime(String strDateTimeToParse) {
        return parseStringToDate("yyyy-MM-dd HH:mm:ss", strDateTimeToParse);
    }

    public static Date parseStringToDate(String strInputDF, String strDateToParse) { // DF = Date Format
        Date outputDate = null;
        SimpleDateFormat inputDateFormat = new SimpleDateFormat(strInputDF);

        try {
            outputDate = inputDateFormat.parse(strDateToParse);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return outputDate;
    }

    public static String parseDateToString(Date inputDate) {
        return parseDateToString("yyyy-MM-dd", inputDate);
    }

    public static String parseDateToString(String strOutputDateFormat, Date inputDate) {
        SimpleDateFormat outputDateFormatter = new SimpleDateFormat(strOutputDateFormat);
        return outputDateFormatter.format(inputDate);
    }

    public static String formattedDateDifference(Date startDate, Date endDate) {
        String outputString = "";

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;
        long monthsInMilli = daysInMilli * 30;

        long elapsedMonths = different / monthsInMilli;
        different = different % monthsInMilli;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        Context context = InteractiveTrainingApp.getContext();

        if (elapsedMonths > 0){
            outputString = context.getString(R.string.past_time) + " " + String.valueOf(elapsedMonths) + context.getString(R.string.month_myanmar);
        } else if (elapsedDays > 0) {
            outputString = context.getString(R.string.past_time) + " " + String.valueOf(elapsedDays) + context.getString(R.string.day_myanmar);
        } else if (elapsedHours > 0) {
            outputString = context.getString(R.string.past_time) + " " + String.valueOf(elapsedHours) + context.getString(R.string.hour_myanmar);
        } else if (elapsedMinutes > 0) {
            outputString = context.getString(R.string.past_time) + " " + String.valueOf(elapsedMinutes) + context.getString(R.string.minute_myanmar);
        } else if (elapsedSeconds > 0) {
            outputString = context.getString(R.string.now_myanmar);
        }

        return outputString;
    }

}