package org.puller;

import org.kohsuke.args4j.Localizable;

import java.util.Locale;

public class LocalizedString implements Localizable {
    private final String msg;

    public LocalizedString(final String msg) {
        this.msg = msg;
    }

    @Override
    public String formatWithLocale(final Locale locale, final Object... args) {
        return String.format(locale, msg, args);
    }

    @Override
    public String format(final Object... args) {
        return formatWithLocale(Locale.getDefault(), args);
    }
}
