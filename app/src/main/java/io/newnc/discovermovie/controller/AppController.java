package io.newnc.discovermovie.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.newnc.discovermovie.model.Movie;
import io.newnc.discovermovie.task.GetCategoriesTask;
import io.newnc.discovermovie.task.GetCompaniesTask;
import io.newnc.discovermovie.task.GetCoversTask;
import io.newnc.discovermovie.task.GetMovieDescriptionTask;
import io.newnc.discovermovie.task.GetMoviesTask;
import io.newnc.discovermovie.task.GetResultTask;
import io.newnc.discovermovie.task.TaskCallback;

/**
 * Application singleton controller.
 */
public class AppController {

    /**
     * A list of loaded products.
     */
    private List<Movie> movies;

    private int cover;
    private int categories;
    private int company;
    private String movieId;

    /**
     * A object implementing the TaskCallback interface, just a try to simulate callback in JS.
     */
    private TaskCallback callbackObject;

    /**
     * Class constructor.
     */
    private AppController() {
    }

    /**
     * Loads movies by pass our API.
     */
    public void loadMovies(TaskCallback callback) {
        log("BEGIN loadMovies");

        callbackObject = callback;
        new GetMoviesTask().execute();

        log("END loadMovies");
    }

    /**
     * This method is called in <code>GetMoviesTask</code> (after <code>loadMovies</code>
     * call), is where in fact the products are loaded.
     *
     * @param callback the json callback from api call.
     */
    public void loadMoviesFrom(JSONObject callback) {
        log("BEGIN loadMoviesFrom");

        if (this.movies == null) this.movies = new ArrayList<>(); else this.movies.clear();

        try {
            JSONArray movies = callback.getJSONArray("movies");
            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie = movies.getJSONObject(i);

                Movie m = new Movie();

                this.movies.add(m);
            }
            callbackObject.doSomething(this.movies);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadMoviesFrom");
    }

    /**
     * Loads covers by pass our API.
     */
    public void loadCovers(TaskCallback callback) {
        log("BEGIN loadCovers");

        callbackObject = callback;
        GetCoversTask task = new GetCoversTask();
        //task.onPostExecute(task.doInBackground());
        task.execute();

        log("END loadCovers");
    }

    /**
     * This method is called in <code>GetCoversTask</code> (after <code>loadCovers</code>
     * call).
     *
     * @param callback the json callback from api call.
     */
    public void loadCoversFrom(JSONArray callback) {
        log("BEGIN loadCoversFrom");

        try {
            String[] coversPath = new String[2];
            for (int i = 0; i < 2; i++)
                coversPath[i] = callback.getString(i);
            callbackObject.doSomething(coversPath);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadCoversFrom");
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public void setCategorie(int categories) {
        this.categories = categories;
    }

    public void setCompany(int company) { this.company = company; }

    /**
     * Loads categories by pass our API.
     */
    public void loadCategories(TaskCallback callback) {
        log("BEGIN loadCategories");

        callbackObject = callback;
        GetCategoriesTask task = new GetCategoriesTask();
        task.execute(cover);

        log("END loadCategories");
    }

    /**
     * This method is called in <code>GetCategoriesTask</code> (after <code>loadCategories</code>
     * call).
     *
     * @param callback the json callback from api call.
     */
    public void loadCategoriesFrom(JSONArray callback) {
        log("BEGIN loadCategoriesFrom");

        try {
            String[] coversPath = new String[4];
            for (int i = 0; i < 4; i++)
                coversPath[i] = callback.getString(i);
            callbackObject.doSomething(coversPath);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadCategoriesFrom");
    }

    /**
     * Loads categories by pass our API.
     */
    public void loadCompanies(TaskCallback callback) {
        log("BEGIN loadCompanies");

        callbackObject = callback;
        GetCompaniesTask task = new GetCompaniesTask();
        task.execute(cover);

        log("END loadCompanies");
    }

    /**
     * This method is called in <code>GetCategoriesTask</code> (after <code>loadCategories</code>
     * call).
     *
     * @param callback the json callback from api call.
     */
    public void loadCompaniesFrom(JSONArray callback) {
        log("BEGIN loadCompaniesFrom");

        try {
            String[] coversPath = new String[4];
            for (int i = 0; i < 4; i++)
                coversPath[i] = callback.getString(i);
            callbackObject.doSomething(coversPath);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadCompaniesFrom");
    }

    /**
     * Loads covers by pass our API.
     */
    public void loadResult(TaskCallback callback) {
        log("BEGIN loadResult");

        callbackObject = callback;
        GetResultTask task = new GetResultTask();
        task.execute(cover, categories);

        log("END loadResult");
    }

    /**
     * This method is called in <code>GetResultTask</code> (after <code>loadResult</code>
     * call).
     *
     * @param callback the json callback from api call.
     */
    public void loadResultFrom(JSONArray callback) {
        log("BEGIN loadResultFrom");

        try {
            String[] coversPath = new String[10];
            for (int i = 0; i < 10; i++)
                coversPath[i] = callback.getString(i);
            callbackObject.doSomething(coversPath);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadResultFrom");
    }

    public void loadDescription(TaskCallback callback) {
        log("BEGIN loadDescription");

        callbackObject = callback;
        GetMovieDescriptionTask task = new GetMovieDescriptionTask();
        task.execute(movieId);

        log("END loadDescription");
    }

    public void loadMovieDescriptionFrom(JSONObject callback) {
        log("BEGIN loadResultFrom");

        try {
            Movie movie = new Movie();

            movie.setTitle(callback.getString("title"));
            movie.setOverview(callback.getString("overview"));
            movie.setCover_path(callback.getString("poster_path"));
            movie.setRelease_date(callback.getString("release_date"));
            movie.setVote_average(callback.getString("vote_average"));

            callbackObject.doSomething(movie);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        log("END loadResultFrom");
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /**
     * Unique instance of this class.
     */
    private static AppController _instance;

    /**
     * Gets the unique instance of this class.
     *
     * @return the unique instance of this class.
     */
    public static AppController getInstance() {
        if (_instance == null)
            _instance = new AppController();

        return _instance;
    }

    private static final String CATEG = "AppController";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
