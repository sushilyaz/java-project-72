package hexlet.code.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatTimestamp {
    public static Timestamp formatTimestamp(Timestamp timestamp) {
        // Создание форматтера с шаблоном без миллисекунд
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return restoreTimestamp(dateFormat.format(new Date(timestamp.getTime())));
    }

    private static Timestamp restoreTimestamp(String formattedTimestamp) {
        // Создание форматтера с тем же шаблоном
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {
            // Парсинг строки и создание объекта Date
            Date parsedDate = dateFormat.parse(formattedTimestamp);

            // Создание объекта Timestamp из объекта Date
            return new Timestamp(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
