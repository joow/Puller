package org.puller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dates {
    public static List<LocalDate> getWednesdays(final LocalDate from, final LocalDate to) {
        final long days = ChronoUnit.DAYS.between(from, to) + 1;
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(days)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                .collect(Collectors.toList());
    }
}
