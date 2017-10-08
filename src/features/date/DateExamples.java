package features.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateExamples {

    public static void main(String[] args) {
        creatingLocalDates();
        addingSubtractingDates();
        differenceBetweenTwoTemporal();
        formatting();
        zone();
    }

    private static void creatingLocalDates() {
        System.out.println("Creating dates");
        System.out.println("Now:");
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());
        System.out.println("Specific date");
        System.out.println(LocalDate.of(1998, 6, 30));
        System.out.println(LocalTime.of(13,48,35,159));
        System.out.println(LocalDateTime.of(1998, 6,30,13,48,35,159));
        System.out.println();
    }

    private static void addingSubtractingDates() {
        System.out.println("Adding and subtracting dates");
        LocalDate now = LocalDate.now();

        System.out.println("Add parts directly");
        System.out.println("Days "+now.plusDays(4));
        System.out.println("Months "+now.plusMonths(3));
        System.out.println("Weeks "+now.plusWeeks(2));
        System.out.println("Years "+now.plusYears(1));
        System.out.println();

        System.out.println("Sub parts directly");
        System.out.println("Days "+now.minusDays(4));
        System.out.println("Months "+now.minusMonths(3));
        System.out.println("Weeks "+now.minusWeeks(2));
        System.out.println("Years "+now.minusYears(1));
        System.out.println();

        System.out.println("Add/sub by parameter");
        System.out.println("Days"+now.plus(2, ChronoUnit.DAYS));
        System.out.println("Decades "+now.minus(2, ChronoUnit.DECADES));
        System.out.println();
    }

    private static void differenceBetweenTwoTemporal() {
        System.out.println("Difference between two temporal");

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusDays(5);

        System.out.println("Now "+now);
        System.out.println("Future "+future);
        System.out.println("Days "+Duration.between(now, future).toDays());
        System.out.println("Minutes "+Duration.between(now, future).toMinutes());

        System.out.println();
    }

    private static void formatting() {
        System.out.println("Formatting");

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Now "+now);
        System.out.println("Basic types");
        System.out.println("BASIC_ISO_DATE "+now.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println("ISO_DATE "+now.format(DateTimeFormatter.ISO_DATE));
        System.out.println("ISO_DATE_TIME "+now.format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("ISO_LOCAL_TIME "+now.format(DateTimeFormatter.ISO_LOCAL_TIME));

        System.out.println("Custom formatting");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        System.out.println("yyyy/MM/dd "+now.format(pattern));
        System.out.println();
    }

    private static void zone() {
        System.out.println("Working with zones");

        ZoneId saoPauloZone = ZoneId.of("America/Sao_Paulo");
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        ZoneId parisZone = ZoneId.of("Europe/Paris");

        //Get the current time with no zone attached to it
        LocalDateTime now = LocalDateTime.now();

        //Tells that 'now' is using sao paulo time's zone
        ZonedDateTime sp = now.atZone(saoPauloZone);

        //based on sp zoned time, I want to know what time is on each zone
        /*
        * if I use the method 'withZoneSameLocal' I am telling to change the current zone of the current hour
        * will not be added ou subtracted any hour
        * */

        ZonedDateTime ny = sp.withZoneSameInstant(newYorkZone);
        ZonedDateTime paris = sp.withZoneSameInstant(parisZone);
        System.out.println("Now in Sao paulo "+sp);
        System.out.println("Now in New York "+ ny);
        System.out.println("Now in Paris "+ paris);

        //If you calculate the difference between to simple dates you will get a error on your calculation
        LocalDateTime day1 = LocalDateTime.of(2017, 11, 10, 10, 15, 35);
        LocalDateTime day2 = LocalDateTime.of(2017, 11, 11, 10, 15, 35);

        System.out.println("Day1 "+day1);
        System.out.println("Day2 "+day2);
        System.out.println("Difference "+Duration.between(day1, day2));

        //applying zones
        ZonedDateTime day1Zoned = day1.atZone(saoPauloZone);
        ZonedDateTime day2Zoned = day2.atZone(newYorkZone);

        System.out.println("Day1 zoned "+day1Zoned);
        System.out.println("Day2 zoned "+day2Zoned);
        System.out.println("Difference "+Duration.between(day1Zoned, day2Zoned));

        System.out.println();
    }
}
