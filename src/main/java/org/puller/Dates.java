package org.puller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dates {
    public static List<LocalDate> getWednesdays(final LocalDate from, final LocalDate to) {
        final int days = Period.between(from, to).getDays() + 1;
        return Stream.iterate(from, date -> date.plusDays(1))
                .limit(days)
                .filter(date -> date.getDayOfWeek() == DayOfWeek.WEDNESDAY)
                .collect(Collectors.toList());
    }
}
