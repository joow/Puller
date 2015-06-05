import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(new File("~/Downloads/in.htm"), "UTF-8");
        final List<Element> elements = document.select("#title > a").stream().map(e -> e.parent().parent()).collect(Collectors.toList());
        final List<Comic> comics = elements.stream().map(
                e -> new Comic(e.select("#title > a").text(), e.select("#price").text())).collect(Collectors.toList());

        comics.forEach(System.out::println);
    }

    public static class Comic {
        private final String title;

        private final String price;

        public Comic(String title, String price) {
            this.title = title;
            this.price = price;
        }

        @Override
        public String toString() {
            return String.format("%s - %s", title, price);
        }
    }
}
