package org.puller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;

public class Configuration {
    private String username;
    private String password;
    private LocalDate from;

    public Configuration(String username, String password, LocalDate from) {
        this.username = username;
        this.password = password;
        this.from = from;
    }

    public void save() {
        final Properties properties = new Properties();
        Optional.ofNullable(username).ifPresent(s -> properties.setProperty("username", s));
        Optional.ofNullable(password).ifPresent(s -> properties.setProperty("password", s));
        Optional.ofNullable(from).ifPresent(f -> properties.setProperty("from", f.format(DateTimeFormatter.ISO_DATE)));

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
}
