package org.puller;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDate;

import static org.testng.Assert.assertEquals;

@Test
public class ConfigurationTest {
    public void itShouldSaveUsername() {
        StringWriter stringWriter = new StringWriter();
        Configuration configuration = new ConfigurationMock("username", null, null, stringWriter);
        configuration.save();

        assertEquals(format(stringWriter), "username=username");
    }

    private String format(StringWriter stringWriter) {
        return stringWriter.toString().replaceAll("#.*", "").replaceAll("\\R", "");
    }

    public void itShouldSavePassword() {
        StringWriter stringWriter = new StringWriter();
        Configuration configuration = new ConfigurationMock(null, "password", null, stringWriter);
        configuration.save();

        assertEquals(format(stringWriter), "password=password");
    }

    public void itShouldSaveFrom() {
        StringWriter stringWriter = new StringWriter();
        Configuration configuration = new ConfigurationMock(null, null, LocalDate.of(2015, 7, 15), stringWriter);
        configuration.save();

        assertEquals(format(stringWriter), "from=2015-07-15");
    }

    private static class ConfigurationMock extends Configuration {
        private final Writer writer;

        public ConfigurationMock(String username, String password, LocalDate from, Writer writer) {
            super(username, password, from);
            this.writer = writer;
        }

        @Override
        Writer getConfigurationWriter() throws IOException {
            return writer;
        }
    }
}