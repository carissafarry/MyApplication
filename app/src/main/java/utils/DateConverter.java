package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static String convertDateFormat(String dateString) {
        try {
            // Define input format
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            // Parse the input date string
            Date date = inputFormat.parse(dateString);

            // Define output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH.mm, dd MMM yyyy", Locale.forLanguageTag("id-ID")); // Locale for Indonesian
            // Format the date to the desired output format
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
