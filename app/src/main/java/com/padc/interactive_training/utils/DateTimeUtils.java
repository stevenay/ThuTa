package com.padc.interactive_training.utils;

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
    *   - 1st arg for date format in string
    *   - 2nd arg for date input in string
    *
    *
    * Contribution for improvement of this utility will be accepted with proper working.
    * Date Formats sample,  "yyyy-MM-dd" , "dd/MM/yyyy", "MM/dd/yyyy", "dd, MMMM, yyyy", "dd, MMM, yyyy"
    * Referenced Java Date Format: https://www.mkyong.com/java/how-to-convert-string-to-date-java/
    * */


    public static Date parseStringToDate(String strDateToParse) {
        return parseStringToDate("yyyy-MM-dd", strDateToParse);
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

    public static String parseDateToString(Date inputDate){
        return parseDateToString("yyyy-MM-dd", inputDate);
    }

    public static String parseDateToString(String strOutputDateFormat, Date inputDate){
        String strOutputDate = "";
        SimpleDateFormat outputDateFormatter = new SimpleDateFormat(strOutputDateFormat);
        strOutputDate = outputDateFormatter.format(inputDate);
        return strOutputDate;
    }



}
