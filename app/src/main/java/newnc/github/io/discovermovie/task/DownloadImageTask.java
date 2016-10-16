package newnc.github.io.discovermovie.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * <code>AsyncTask</code> to download images.
 * Credits: http://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView image;

    private String IMGURL = "https://image.tmdb.org/t/p/w500";

    /**
     * Class constructor.
     *
     * @param image the <code>ImageView</code> to load the image.
     */
    public DownloadImageTask(ImageButton image) {
        this.image = image;
    }

    /**
     * Downloads the image outside the UI thread.
     *
     * @param urls a array of urls to be downloaded. In fact, only one url is downloaded, the first
     *             in the array.
     * @return the image loaded.
     */
    @Override
    protected Bitmap doInBackground(String... urls) {
        log("BEGIN doInBackground");

        String url = IMGURL + urls[0];
        Bitmap bitmap = null;

        try {
            InputStream in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            Log.i("DownloadImageTask", "Image downloaded with success.");
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        log("END doInBackground");

        return bitmap;
    }

    /**
     * Sets the image to the <code>ImageView</code> after download it.
     *
     * @param bitmap the image downloaded.
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        log("BEGIN onPostExecute");

        image.setImageBitmap(bitmap);

        log("END onPostExecute");
    }

    private static final String CATEG = "DownloadImageTask";
    private void log(String message) {
        Log.v(CATEG, message);
    }
}
