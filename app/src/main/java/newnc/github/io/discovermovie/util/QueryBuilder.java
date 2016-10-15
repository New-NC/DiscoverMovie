package newnc.github.io.discovermovie.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private String url = "";
    private String service = "";
    private boolean covers = false;
    private Integer categoryId = null;

    public QueryBuilder url(String url) {
        this.url = url;

        return this;
    }

    public QueryBuilder service(String service) {
        this.service = service;

        return this;
    }

    public QueryBuilder covers() {
        covers = true;
        categoryId = null;

        return this;
    }

    public QueryBuilder category(int id) {
        categoryId = new Integer(id);
        covers = false;

        return this;
    }

    public QueryBuilder clear() {
        categoryId = null;
        covers = false;

        return this;
    }

    public QueryBuilder reset() {
        clear();

        url = service = "";

        return this;
    }

    public boolean validate() {
        if (url == null || url.isEmpty()
                || service == null)
            return false;
        return true;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        if (url.isEmpty()) return null; else stringBuilder.append(url);
        if (!url.endsWith("/"))
            stringBuilder.append("/");
        if (service.isEmpty()) return null; else stringBuilder.append(service);
        if (covers) stringBuilder.append("/covers");

        String query = stringBuilder.substring(0);

        Log.i(toString(), query);

        return query;
    }
}
