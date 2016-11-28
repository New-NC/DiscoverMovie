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

import io.newnc.discovermovie.view.Results;

/**
 * <code>AsyncTask</code> to download images.
 * Credits: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
public class DownloadImageTask extends AsyncTask<String, Void, String> {
    private Context context;
    private ImageView image;
    private ProgressBar progressBar;

    private String IMG_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * Class constructor.
     */
    public DownloadImageTask(Context c, ImageButton i, ProgressBar p){
        this.context = c;
        this.image = i;
        this.progressBar = p;
    }

    /*
        Downloads the image outside the UI thread.
    */

    @Override
    protected String doInBackground(String... s) {
        return s[0];
    }

    /**
     * Sets the image to the <code>ImageView</code> after download it.
     *
     */
    @Override
    protected void onPostExecute(String s) {
        log("BEGIN onPostExecute");

        String url = IMG_URL + s;
        int rWidth, rHeight;
        // w500 of TMDB is 500x750
        rWidth = 500;
        rHeight = 750;

        if(context.getClass() == Results.class) {
            log("Results");
            Picasso.with(context).load(url).resize(rWidth / 2, rHeight / 2).into(image);
        }
        else {
            log("Not Results");
            Picasso.with(context).load(url).resize(rWidth / 3, rHeight / 3).into(image);
        }

        image.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        log("END onPostExecute");
    }

    private static final String CATEG = "DownloadImageTask";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
