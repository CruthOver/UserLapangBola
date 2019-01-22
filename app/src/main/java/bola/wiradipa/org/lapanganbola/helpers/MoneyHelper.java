package bola.wiradipa.org.lapanganbola.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MoneyHelper {
    public static String toMoneyWithRp(String str_money){
        if(str_money==null)
            return "Rp -";
        DecimalFormat converter;
        converter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        converter.setMinimumFractionDigits(0);

        try {
            double money = Double.parseDouble(str_money);
            return "Rp "+converter.format(money);
        } catch (NumberFormatException e){
            return "Rp -";
        }
    }

    public static String toMoney(String str_money){
        DecimalFormat converter;
        converter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.GERMAN);
        converter.setMinimumFractionDigits(0);

        try {
            double money = Double.parseDouble(str_money);
            return converter.format(money);
        } catch (NumberFormatException e){
            return "0";
        }
    }
}
