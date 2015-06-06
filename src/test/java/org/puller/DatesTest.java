package org.puller;

import org.testng.annotations.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test
public class DatesTest {
    public void itShouldNotGenerateAnyDatesIfNoWednesdays() {
        final LocalDate from = LocalDate.of(2015, 6, 6);
        final LocalDate to = LocalDate.of(2015, 6, 9);

        assertTrue(Dates.getWednesdays(from, to).isEmpty());
    }

    public void itShouldGenerateOneWednesdayForOneWeek() {
        final LocalDate from = LocalDate.of(2015, 6, 1);
        final LocalDate to = LocalDate.of(2015, 6, 6);

        assertEquals(Dates.getWednesdays(from, to).size(), 1);
    }

    public void itShouldContainFromIfItIsAWednesday() {
        final LocalDate from = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        final LocalDate to = from.plusDays(6);

        assertTrue(Dates.getWednesdays(from, to).contains(from));
    }

    public void itShouldContainToIfItIsAWednesday() {
        final LocalDate from = LocalDate.now();
        final LocalDate to = from.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        assertTrue(Dates.getWednesdays(from, to).contains(to));
    }

    public void itShouldContainBothFromAndToIfTheyAreWednesdays() {
        final LocalDate from = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.WEDNESDAY));
        final LocalDate to = from.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));

        final List<LocalDate> wednesdays = Dates.getWednesdays(from, to);

        assertEquals(wednesdays.size(), 2);
        assertTrue(wednesdays.contains(from));
        assertTrue(wednesdays.contains(to));
    }
}