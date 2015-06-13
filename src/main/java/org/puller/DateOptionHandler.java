package org.puller;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateOptionHandler extends OneArgumentOptionHandler<LocalDate> {
    public DateOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super LocalDate> setter) {
        super(parser, option, setter);
    }

    @Override
    protected LocalDate parse(String argument) throws CmdLineException {
        try {
            return LocalDate.parse(argument, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new CmdLineException(owner, new LocalizedString("{0} is not a valid date"), argument);
        }
    }
}
