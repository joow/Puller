package org.puller;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class PullerOptions {
    @Option(name = "-u", aliases = "--username", metaVar = "<username>", usage = "Comixology username")
    private String username;

    @Option(name = "-p", aliases = "--password", metaVar = "<password>", usage = "Comixology password")
    private String password;

    @Option(name = "-f", aliases = "--from", metaVar = "<from>", usage = "Starting date")
    private LocalDate from = LocalDate.now();

    @Option(name = "-t", aliases = "--to", metaVar = "<to>", usage = "Ending date")
    private LocalDate to = from.plusDays(6);

    @Option(name = "--dry-run", usage = "Don't save any data (username, password or last ending date)")
    private boolean dryRun;

    @Option(name = "-h", aliases = "--help", usage = "Display help information")
    private boolean displayHelp;

    @Argument
    private List<String> arguments = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public boolean isDisplayHelp() {
        return displayHelp;
    }
}
