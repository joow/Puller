package org.puller.comixology;

import com.squareup.okhttp.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.puller.Comic;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Comixology {
    private static final String COMIXOLOGY_PULL_LIST_BASE_URL = "https://pulllist.comixology.com/";

    private final ComixologyUser user;
    private final ComixologyService service;

    private boolean isNotLoggedIn = true;

    public Comixology(final String username, final String password) {
        this.user = new ComixologyUser(username, password);
        this.service = createService();
    }

    private ComixologyService createService() {
        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);

        final OkHttpClient httpClient = new OkHttpClient();
        httpClient.setCookieHandler(cookieManager);

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(COMIXOLOGY_PULL_LIST_BASE_URL)
                .setClient(new OkClient(httpClient))
                .setConverter(new HtmlConverter())
                .build();

        return restAdapter.create(ComixologyService.class);
    }

    public List<Comic> getWeeklyPullList(final LocalDate date) {
        final String csrfToken = getCsrfToken();

        if (isNotLoggedIn) {
            service.login(user.toMap(), csrfToken);
            isNotLoggedIn = false;
        }

        final String year = String.format(Locale.ENGLISH, "%tY", date);
        final String month = String.format(Locale.ENGLISH, "%tm", date);
        final String dayOfMonth = String.format(Locale.ENGLISH, "%td", date);
        final String weeklyPullList = service.getWeeklyPullList(year, month, dayOfMonth);

        return parse(weeklyPullList);
    }

    private List<Comic> parse(final String pullList) {
        final Document document = Jsoup.parse(pullList);

        return document.select("#title > a").stream()
                .map(e -> e.parent().parent())
                .map(e -> new Comic(e.select("#title > a").text(), e.select("#price").text()))
                .collect(Collectors.toList());
    }

    private String getCsrfToken() {
        final String index = service.getIndex();
        return Jsoup.parse(index).select("input[name=PL_CSRF_TOKEN]").attr("value");
    }
}
