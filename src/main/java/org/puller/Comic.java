package org.puller;

public class Comic implements Comparable<Comic> {
    private static final String DOLLAR_SYMBOL = "\\$";

    private final String title;
    private final String price;

    public Comic(final String title, final String price) {
        this.title = title;
        this.price = price;
    }

    public String getRawPrice() {
        return price.replaceAll(DOLLAR_SYMBOL, "");
    }

    @Override
    public String toString() {
        return String.format("%s - %s", title, price);
    }

    @Override
    public int compareTo(Comic comic) {
        return title.compareTo(comic.title);
    }
}
