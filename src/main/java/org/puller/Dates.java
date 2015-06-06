package org.puller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Dates {
    public static List<LocalDate> getWednesdays(final LocalDate from, final LocalDate to) {
        final List<LocalDate> wednesdays = new ArrayList<>();

        LocalDate nextWednesday = from.with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        while (nextWednesday.isBefore(to) || nextWednesday.isEqual(to)) {
            wednesdays.add(nextWednesday);
            nextWednesday = nextWednesday.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        }

        return wednesdays;
    }
}
