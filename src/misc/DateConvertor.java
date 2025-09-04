package misc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateConvertor
{
    // define a formatter for "dd-MM-yyyy"
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * Converts a LocalDate to a String in the format DD-MM-YYYY.
     *
     * @param date the LocalDate to format
     * @return formatted date string ("dd-MM-yyyy")
     */
    public static String formatLocalDate(LocalDate date) {
        return date.format(FORMATTER);
    }

    /**
     * Parses a String in the format DD-MM-YYYY into a LocalDate.
     *
     * @param dateString the date string ("dd-MM-yyyy")
     * @return the corresponding LocalDate
     * @throws DateTimeParseException if the text cannot be parsed
     */
    public static LocalDate parseToLocalDate(String dateString) {
        return LocalDate.parse(dateString, FORMATTER);
    }
}
