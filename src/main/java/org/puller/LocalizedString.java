package org.puller;

import org.kohsuke.args4j.Localizable;

import java.text.MessageFormat;
import java.util.Locale;

public class LocalizedString implements Localizable {
    private final String msg;

    public LocalizedString(final String msg) {
        this.msg = msg;
    }

    @Override
    public String formatWithLocale(final Locale locale, final Object... args) {
        return MessageFormat.format(msg, args);
    }

    @Override
    public String format(final Object... args) {
        return formatWithLocale(Locale.getDefault(), args);
    }
}
