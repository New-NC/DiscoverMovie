package io.newnc.discovermovie.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import io.newnc.discovermovie.view.MovieDescription;
import io.newnc.discovermovie.view.Results;

/**
 * <code>AsyncTask</code> to download images.
 * Credits: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
public class DownloadImageTask extends AsyncTask<String, Void, RequestCreator> {
    private Context context;
    private ImageView image;
    private ProgressBar progressBar;

    private String IMG_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Class constructor.
     */
    public DownloadImageTask(Context c, ImageView i, ProgressBar p){
        this.context = c;
        this.image = i;
        this.progressBar = p;
    }

    /*
        Downloads the image outside the UI thread.
    */

    @Override
    protected RequestCreator doInBackground(String... s) {

        String url = IMG_URL + s[0];
        RequestCreator a = null;

        int rWidth, rHeight;
        // w500 of TMDB is 500x750
        rWidth = 500;
        rHeight = 750;

        if (context.getClass() == Results.class) {
            log("Results"); // imagem maior
            rWidth /= 2;
            rHeight /= 2;
        } else {
            log("Not Results"); // imagem menor
            rWidth /= 4;
            rHeight /= 4;
        }

        try {
            a = Picasso.with(context).load(url).resize(rWidth, rHeight);
        }
        catch(Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return a;
    }

    /**
     * Sets the image to the <code>ImageView</code> after download it.
     *
     */
    @Override
    protected void onPostExecute(RequestCreator a) {
        log("BEGIN onPostExecute");

        a.into(image);
        image.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        if (context instanceof MovieDescription) {
            MovieDescription movieDescription = (MovieDescription) context;
            movieDescription.prepareContent();
        }

        log("END onPostExecute");
    }

    private static final String CATEG = "DownloadImageTask";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
