package vol1.ch9;

import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * тестирую свойства даты и календаря
 */
public class TestCalendar {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ITALY);
        GregorianCalendar calendar = new GregorianCalendar();
        System.out.println("Now is "+Locale.getDefault()+" : "+calendar.getTime());


    }

}
