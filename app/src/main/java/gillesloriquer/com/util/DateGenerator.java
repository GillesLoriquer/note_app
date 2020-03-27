package gillesloriquer.com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateGenerator {

    public static String getCurrentTimestamp() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-yyyy", Locale.FRANCE);
        String stringDate = simpleDateFormat.format(new Date());
        String month = getMonthFromNumber(stringDate.substring(0, 2));
        return stringDate.replaceAll("^\\d{2}-", month + " ");
    }

    private static String getMonthFromNumber(String monthNumber) {

        switch (monthNumber) {
            case "01":
                return "Jan";
            case "02":
                return "Feb";
            case "03":
                return "Mar";
            case "04":
                return "Apr";
            case "05":
                return "May";
            case "06":
                return "Jun";
            case "07":
                return "Jul";
            case "08":
                return "Aou";
            case "09":
                return "Sep";
            case "10":
                return "Oct";
            case "11":
                return "Nov";
            case "12":
                return "Dev";
            default:
                return "Error";
        }
    }
}
