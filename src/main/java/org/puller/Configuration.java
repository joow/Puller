package org.puller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Configuration {
    private String username;
    private String password;
    private LocalDate from;

    public Configuration() {
        final Properties properties = new Properties();
        try {
            properties.load(new FileReader(getConfigurationFilePath()));
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            from = LocalDate.parse(properties.getProperty("from"));
        } catch (IOException e) {
            System.out.println("No configuration file found.");
        }
    }

    public Configuration(String username, String password, LocalDate from) {
        this.username = username;
        this.password = password;
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void save() {
        final Properties properties = new Properties();
        Optional.ofNullable(username).ifPresent(s -> properties.setProperty("username", s));
        Optional.ofNullable(password).ifPresent(s -> properties.setProperty("password", s));
        Optional.ofNullable(from).ifPresent(d -> properties.setProperty("from", d.format(DateTimeFormatter.ISO_DATE)));

        try {
            properties.store(getConfigurationWriter(), null);
        } catch (IOException e) {
            e.printStackTrace(); // TODO logging
        }
    }

    Writer getConfigurationWriter() throws IOException {
        final String configFilePath = getConfigurationFilePath();
        final Path configFile = Paths.get(configFilePath);

        if (Files.notExists(configFile)) {
            Files.createDirectories(configFile.getParent());
            Files.createFile(configFile);
        }

        return new FileWriter(getConfigurationFilePath());
    }

    private String getConfigurationFilePath() {
        String configHome = System.getProperty("XDG_CONFIG_HOME");

        if (configHome == null || configHome.isEmpty()) {
            configHome = System.getProperty("user.home");
        }

        return configHome + File.separator + ".config" + File.separator + "puller";
    }

    public Collection<String> getArguments() {
        Collection<String> args = new ArrayList<>();
        Optional.ofNullable(username).ifPresent(s -> args.addAll(Arrays.asList("-u", s)));
        Optional.ofNullable(password).ifPresent(s -> args.addAll(Arrays.asList("-p", s)));
        Optional.ofNullable(from).ifPresent(d -> args.addAll(Arrays.asList("-f", d.format(DateTimeFormatter.ISO_DATE))));

        return args;
    }
}
