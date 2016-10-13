package newnc.github.io.discovermovie.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private String url = "";
    private String service = "";
    private String searchBy = "";
    private String key = "";
    private int pageSize = 10;
    private int page = 0;
    private String sortBy = "";
    private boolean sortAscendent = true;
    private List<String> whatToShow;
    private String callbackType = "";
    private boolean jsonFormat = false;

    /**
     * Class constructor.
     */
    public QueryBuilder() {
        whatToShow = new ArrayList<>();
    }

    public QueryBuilder url(String url) {
        this.url = url;

        return this;
    }

    public QueryBuilder service(String service) {
        this.service = service;

        return this;
    }

    public QueryBuilder searchBy(String searchBy) {
        this.searchBy = searchBy;

        return this;
    }

    public QueryBuilder key(String key) {
        this.key = key;

        return this;
    }

    public QueryBuilder pageSize(int pageSize) {
        this.pageSize = pageSize;

        return this;
    }

    public QueryBuilder page(int page) {
        this.page = page;

        return this;
    }

    public QueryBuilder previousPage() {
        page--;

        return this;
    }

    public QueryBuilder nextPage() {
        page++;

        return this;
    }

    public QueryBuilder sortBy(String sortBy) {
        this.sortBy = sortBy;
        sortAscendent = true;

        return this;
    }

    public QueryBuilder sortBy(String sortBy, boolean desc) {
        this.sortBy = sortBy;
        sortAscendent = !desc;

        return this;
    }

    public QueryBuilder show(String whatToShow) {
        this.whatToShow.add(whatToShow);

        return this;
    }

    public QueryBuilder callbackType(String callbackType) {
        this.callbackType = callbackType;

        return this;
    }

    public QueryBuilder jsonFormat(boolean jsonFormat) {
        this.jsonFormat = jsonFormat;

        return this;
    }

    public boolean validate() {
        if (url == null || url.isEmpty()
                || service == null /*|| service.isEmpty()
                || searchBy == null
                || key == null || key.isEmpty()
                || sortBy == null*/)
            return false;
        return true;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        if (url.isEmpty()) return null; else stringBuilder.append(url);
        if (!url.endsWith("/"))
            stringBuilder.append("/");
        if (service.isEmpty()) return null; else stringBuilder.append(service);
        /*if (!searchBy.isEmpty()) stringBuilder.append("((" + searchBy + "))");
        if (key.isEmpty()) return null; else stringBuilder.append("?apiKey=" + key);
        if (!sortBy.isEmpty()) {
            stringBuilder.append("&sort=" + sortBy);
            if (sortAscendent)
                stringBuilder.append(".asc");
            else
                stringBuilder.append(".desc");
        }
        if (!whatToShow.isEmpty()) {
            stringBuilder.append("&show=" + whatToShow.get(0));
            for (String what : whatToShow.subList(1, whatToShow.size()))
                stringBuilder.append("," + what);
        }
        if (jsonFormat)
            stringBuilder.append("&callback=JSON_CALLBACK&format=json");*/

        String query = stringBuilder.substring(0);

        Log.i(toString(), query);

        return query;
    }
}
