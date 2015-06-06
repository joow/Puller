package org.puller;

public class Comic {
    private final String title;
    private final String price;

    public Comic(final String title, final String price) {
        this.title = title;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", title, price);
    }
}
