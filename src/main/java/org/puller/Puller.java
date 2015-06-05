package org.puller;

import org.jsoup.Jsoup;
import org.puller.comixology.ComixologyService;
import org.puller.comixology.HtmlConverter;
import retrofit.RestAdapter;

public class Puller {
    public static void main(String[] args) {
        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://pulllist.comixology.com/")
                .setConverter(new HtmlConverter()).build();
        final ComixologyService service = restAdapter.create(ComixologyService.class);

        final String index = service.getIndex();
        final String csrfToken = Jsoup.parse(index).select("input[name=PL_CSRF_TOKEN]").attr("value");

        System.out.println(csrfToken);
    }
}
