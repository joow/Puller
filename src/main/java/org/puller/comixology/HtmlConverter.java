package org.puller.comixology;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class HtmlConverter implements Converter {
    @Override
    public String fromBody(TypedInput body, Type type) throws ConversionException {
        final List<String> lines = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(body.in()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new ConversionException(e);
        }

        return lines.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public TypedOutput toBody(Object object) {
        throw new UnsupportedOperationException();
    }
}
