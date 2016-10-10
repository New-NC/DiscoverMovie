package newnc.github.io.discovermovie.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import newnc.github.io.discovermovie.model.Movie;
import newnc.github.io.discovermovie.task.GetMoviesTask;
import newnc.github.io.discovermovie.task.TaskCallback;

/**
 * Application singleton controller.
 */
public class AppController {

    /**
     * A list of loaded products.
     */
    private List<Movie> movies;

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
            JSONArray movies = callback.getJSONArray("products");
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
