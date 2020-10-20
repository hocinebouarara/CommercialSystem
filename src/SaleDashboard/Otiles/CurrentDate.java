package SaleDashboard.Otiles;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CurrentDate {
    public static java.util.Date CurrentDate(){

        LocalDate localDate=java.time.LocalDate.now();

        java.util.Date date=Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return date;
    }
}
