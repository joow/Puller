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
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Comixology {
    private static final Logger LOGGER = Logger.getLogger(Comixology.class.getName());

    private static final String COMIXOLOGY_PULL_LIST_BASE_URL = "https://pulllist.comixology.com/";

    private static final String LOGIN_INFO_COOKIE_NAME = "LOGIN_INFO";

    private final ComixologyUser user;
    private final ComixologyService service;

    private boolean isNotLoggedIn = true;

    private final CookieManager cookieManager = new CookieManager();

    public Comixology(final String username, final String password) {
        this.user = new ComixologyUser(username, password);
        this.service = createService();
    }

    private ComixologyService createService() {
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
        if (isNotLoggedIn) {
            LOGGER.info("Logging into Comixology...");
            final String csrfToken = getCsrfToken();
            service.login(user.toMap(), csrfToken);

            // TODO IMPROVE !
            cookieManager.getCookieStore().getCookies().stream()
                    .filter(c -> LOGIN_INFO_COOKIE_NAME.equals(c.getName()))
                    .findFirst().orElseThrow(() -> new RuntimeException("Not authenticated !"));
            LOGGER.info("Successfully logged in.");
            isNotLoggedIn = false;
        }

        LOGGER.info(String.format("Fetching weekly pull list for %s...", date));
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
