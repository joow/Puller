package org.puller;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.ArrayList;
import java.util.List;

class PullerOptions {
    @Option(name = "-u", aliases = "--username", metaVar = "<user>", usage = "Comixology username")
    private String username;

    @Option(name = "-p", aliases = "--password", metaVar = "<password>", usage = "Comixology password")
    private String password;

    @Argument
    private List<String> arguments = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
