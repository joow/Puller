package org.puller;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerRegistry;
import org.puller.comixology.Comixology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Puller {
    private static final Logger LOGGER = Logger.getLogger(Puller.class.getName());

    public static void main(String[] args) {
        OptionHandlerRegistry.getRegistry().registerHandler(LocalDate.class, DateOptionHandler.class);
        final PullerOptions options = new PullerOptions();
        final CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(new Configuration().getArguments());
            parser.parseArgument(args);

            if (options.getUsername() == null) {
                throw new CmdLineException(parser, new LocalizedString("The username is missing"));
            }

            if (options.getPassword() == null) {
                throw new CmdLineException(parser, new LocalizedString("The password is missing"));
            }

            pull(options);

            if (!options.isDryRun()) {
                new Configuration(options.getUsername(), options.getPassword(), options.getTo()).save();
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("usage: puller [options]");
            new CmdLineParser(new PullerOptions()).printUsage(System.err);
        }
    }

    private static void pull(final PullerOptions options) {
        final Comixology comixology = new Comixology(options.getUsername(), options.getPassword());
        final List<LocalDate> wednesdays = Dates.getWednesdays(options.getFrom(), options.getTo());

        LOGGER.info(String.format("Fetching Comixology pull list for %s from %s to %s...", options.getUsername(),
                options.getFrom(), options.getTo()));

        final List<Comic> comics = wednesdays.stream()
                .map(comixology::getWeeklyPullList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println();
        System.out.println(String.format("%s's pull list from %s to %s", options.getUsername(), options.getFrom(), options.getTo()));
        System.out.println("-------------------------------");
        comics.stream().sorted().forEach(System.out::println);
        System.out.println("-------------------------------");
        System.out.println(String.format("Total = $%5.2f",
                comics.stream().map(Comic::getRawPrice).collect(Collectors.summingDouble(Double::valueOf))));
    }
}
