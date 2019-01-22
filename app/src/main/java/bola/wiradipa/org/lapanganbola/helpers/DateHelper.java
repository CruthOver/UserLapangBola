package bola.wiradipa.org.lapanganbola.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static String[] months = {"Bulan", "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"};

    public static String[] days = {"Hari", "Senin", "Selasa", "Rabu", "Kamis", "Jum'at", "Sabtu",
            "Minggu"};

    public final static int MONTH = Calendar.MONTH;
    public final static int YEAR = Calendar.YEAR;
    public final static int DAY = Calendar.DAY_OF_MONTH;
    public final static int HOUR = Calendar.HOUR;

    public static String getDate(Date date){
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        return dfDay.format(date)+" "+months[Integer.parseInt(dfMonth.format(date))]+" "+
                dfYear.format(date);
    }

    public static String getDateWithNameDay(Date date){
        SimpleDateFormat dfDayofMonth = new SimpleDateFormat("u");
        SimpleDateFormat dfMonth = new SimpleDateFormat("M");
        SimpleDateFormat dfDay = new SimpleDateFormat("dd");
        SimpleDateFormat dfYear = new SimpleDateFormat("yyyy");

        return days[Integer.parseInt(dfDayofMonth.format(date))]+", "+dfDay.format(date)+" "+
                months[Integer.parseInt(dfMonth.format(date))]+" "+
                dfYear.format(date);
    }

    public static String getDateServer(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }

    public static Date addDate(Date date, int field, int addition){
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        calendar.add(field, addition);
        return calendar.getTime();
    }

    public static Date parsingDate(String pattern, String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(String pattern, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
}
