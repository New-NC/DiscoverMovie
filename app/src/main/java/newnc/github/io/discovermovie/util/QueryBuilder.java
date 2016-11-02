package newnc.github.io.discovermovie.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private String url = "";
    private String service = "";
    private boolean covers = false;
    private Integer categoryId = null;
    private Integer typeId = null;

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
        typeId = null;

        return this;
    }

    public QueryBuilder category(int id) {
        categoryId = new Integer(id);
        covers = false;
        typeId = null;

        return this;
    }

    public QueryBuilder result(int typeId, int categoryId) {
        this.typeId = new Integer(typeId);
        this.categoryId = new Integer(categoryId);
        covers = false;

        Log.i(toString(), this.typeId.toString());

        return this;
    }

    public QueryBuilder clear() {
        categoryId = null;
        covers = false;
        typeId = null;

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
        if (covers && (categoryId != null || typeId != null))
            return false;
        if (categoryId != null && covers)
            return false;
        if (typeId != null && (covers || categoryId == null))
            return false;
        return true;
    }

    public String build() {
        StringBuilder stringBuilder = new StringBuilder();

        if (url.isEmpty())
            return null;
        else
            stringBuilder.append(url);

        if (!url.endsWith("/"))
            stringBuilder.append("/");

        if (service.isEmpty())
            return null;
        else
            stringBuilder.append(service);

        if (covers)
            stringBuilder.append("/covers");

        if (typeId != null && categoryId != null)
            stringBuilder.append("/results/" + typeId.intValue() + "/" + categoryId.intValue());
        else if
            (categoryId != null) stringBuilder.append("/categories/" + categoryId.intValue());

        String query = stringBuilder.substring(0);

        Log.i(toString(), query);

        return query;
    }

    public static final String LANURL = "http://192.168.1.41:8080/";
    public static final String HEROKUURL = "https://infinite-brushlands-81913.herokuapp.com";
    public static final String URL = HEROKUURL;
}
