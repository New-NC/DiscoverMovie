package io.newnc.discovermovie.task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;

import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.util.QueryBuilder;

/**
 * Created by guilhermejcgois on 12/1/16.
 */
public class GetMovieDescriptionTask extends AsyncTask<String, Void, String> {

    /**
     * Class constructor.
     */
    public GetMovieDescriptionTask() {
        setVV();
    }

    /**
     * Builds and execute the query to load products.
     *
     * @param params not important.
     * @return the json callback from the query.
     */
    @Override
    public String doInBackground(String... params) {
        log("BEGIN doInBackground");

        if (params.length < 1)
            throw new InvalidParameterException("Too few arguments.");


        // http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
        HttpURLConnection connection = null;
        try {
            String query = "https://api.themoviedb.org/3/movie/" + params[0] + "?api_key=988bfbce3f85f6688647dfb4f5d7a5a9&language=en-US";
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
    public void onPostExecute(String s) {
        log("BEGIN onPostExecute");

        try {
            if (s != null)
                AppController.getInstance().loadMovieDescriptionFrom(new JSONObject(s));
            else
                log("null");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        log("END onPostExecute");
    }

    private static final String CATEG = "GetMovieDescriptionTask";
    private static int v = 0;
    private int vv;
    private void setVV() {
        vv = v++;
    }
    private void log(String message) {
        Log.v(CATEG + vv, message);
    }

}
