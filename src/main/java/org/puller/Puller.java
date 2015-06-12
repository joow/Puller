package org.puller;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.puller.comixology.Comixology;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Puller {
    public static void main(String[] args) {
        final PullerOptions options = new PullerOptions();
        final CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);

            if (options.getUsername() == null) {
                throw new CmdLineException(parser, new LocalizedString("The username is missing"));
            }

            if (options.getPassword() == null) {
                throw new CmdLineException(parser, new LocalizedString("The password is missing"));
            }

            pull(options);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("usage: puller [options]");
            new CmdLineParser(new PullerOptions()).printUsage(System.err);
        }
    }

    private static void pull(final PullerOptions options) {
        final LocalDate from = LocalDate.of(2015, 6, 10);
        final LocalDate to = LocalDate.now();
        final List<LocalDate> wednesdays = Dates.getWednesdays(from, to);
        final Comixology comixology = new Comixology(options.getUsername(), options.getPassword());
        final List<Comic> comics = wednesdays.stream()
                .map(comixology::getWeeklyPullList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        comics.stream().sorted().forEach(System.out::println);
        System.out.println("-------------------------------");
        System.out.println(comics.stream().map(Comic::getRawPrice).collect(Collectors.summingDouble(Double::valueOf)));
    }
}
