package io.newnc.discovermovie.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.util.QueryBuilder;

/**
 * <code>AsyncTask</code> to get the products from bestbuy.com.
 * This is the query: https://api.bestbuy.com/v1/products((categoryPath.id=pcmcat209400050001))
 *      ?apiKey=sxwj3hhtpnkqex9m95tc7cb5
 *      &sort=bestSellingRank.asc
 *      &show=bestSellingRank,name,manufacturer,regularPrice,salePrice,image,thumbnailImage,
 *          description,longDescription,shortDescription
 *      &callback=JSON_CALLBACK
 *      &format=json
 */
public class GetMoviesTask extends AsyncTask<Void, Void, String> {

    /**
     * A <code>QueryBuilder</code> to build the query.
     */
    private QueryBuilder queryBuilder = null;

    /**
     * Class constructor.
     */
    public GetMoviesTask() {
        setVV();
    }

    /**
     * Builds and execute the query to load products.
     *
     * @param params not important.
     * @return the json callback from the query.
     */
    @Override
    protected String doInBackground(Void... params) {
        log("BEGIN doInBackground");

        if (queryBuilder == null) {
            queryBuilder = new QueryBuilder()
                    .url(QueryBuilder.URL)
                    .service("movies");
        }

        // http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
        HttpURLConnection connection = null;
        try {
            if (!queryBuilder.validate())
                throw new MalformedURLException();
            String query = queryBuilder.build();
            log(query);
            URL url = new URL(query);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-length", "0");
            connection.setUseCaches(false);
            connection.setAllowUserInteraction(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            switch (connection.getResponseCode()) {
                case 200:
                case 201:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String s;
                    while ((s = reader.readLine()) != null)
                        stringBuilder.append(s).append("\n");
                    reader.close();
                    log("END1 doInBackground");
                    return stringBuilder.substring(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        log("END2 doInBackground");
        return null;
    }

    /**
     * Do the job of call the method to convert the json callback in a <code>List</code> of
     * <code>Products</code>.
     *
     * @param s the json callback from querying the api.
     */
    @Override
    protected void onPostExecute(String s) {
        log("BEGIN onPostExecute");

        try {
            if (s != null)
                AppController.getInstance().loadMoviesFrom(new JSONObject(s.substring(s.indexOf('{'))));
            else
                log("null");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        log("END onPostExecute");
    }

    private static final String CATEG = "GetMoviesTask";
    private static int v = 0;
    private int vv;
    private void setVV() {
        vv = v++;
    }
    private void log(String message) {
        Log.v(CATEG + vv, message);
    }
}
