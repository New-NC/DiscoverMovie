package io.newnc.discovermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.io.File;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.model.Movie;
import io.newnc.discovermovie.task.DownloadImageTask;
import io.newnc.discovermovie.task.TaskCallback;

public class MovieDescription extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    private ShareActionProvider shareActionProvider;

    private TextView titleText;
    private ImageView coverImage;
    private ProgressBar progressBar;
    private TextView releaseDateText;
    private TextView voteAverageText;
    private TextView overviewText;

    private Button backButton;

    private Toolbar mToolbar;

    private String coverPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_movie);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        titleText = (TextView) findViewById(R.id.movie_Title);
        coverImage = (ImageView) findViewById(R.id.movie_cover);
        progressBar = (ProgressBar) findViewById(R.id.movie_cover_progress);
        releaseDateText = (TextView) findViewById(R.id.movie_release_date);
        voteAverageText = (TextView) findViewById(R.id.movie_vote_avg);
        overviewText = (TextView) findViewById(R.id.overview);

        titleText.setMovementMethod(new ScrollingMovementMethod());
        overviewText.setMovementMethod(new ScrollingMovementMethod());

        backButton = (Button) findViewById(R.id.returnResults);

        setClickListener();

        AppController.getInstance().loadDescription(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description_movie_menu, menu);

        MenuItem item = menu.findItem(R.id.share);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        return true;
    }

    public void setShareIntent(Bitmap image) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBodyText = "Hey, I'm watching " + titleText.getText() + "!";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, titleText.getText());
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        File coverFile = new File(Environment.getDownloadCacheDirectory().getPath(), "cover.jpg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(coverFile));

        if (shareActionProvider != null)
            shareActionProvider.setShareIntent(sharingIntent);

        final ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();
        final SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        shareActionProvider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                if ("com.facebook.katana".equals(intent.getComponent().getPackageName())) {
                    shareDialog.show(content);
                }
                return false;
            }
        });
    }

    private void setClickListener() {
        backButton.setOnClickListener(this);
    }

    public void startQuestionnaire(View view) {
        Intent myIntent = new Intent(view.getContext(), FirstQuestion.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void doSomething(Object o) {
        Movie movie = (Movie) o;

        titleText.setText(movie.getTitle());
        new DownloadImageTask(this, coverImage, progressBar).execute(coverPath = movie.getCover_path());
        releaseDateText.setText("Release Date: " + movie.getRelease_date());
        voteAverageText.setText("Vote Average: " + movie.getVote_average());
        overviewText.setText(movie.getOverview());
    }

    private static final String CATEG = "MovieDescription";
    private void log(String message) {
        Log.v(CATEG, message);
    }
    private void error(String message, Exception exception) { Log.v(CATEG, message, exception); }

    @Override
    public void onClick(View v) {
        if (buttonReturnResults(v))
            finish();
    }

    /* AUXILIARY FUNCTIONS */
    public boolean buttonReturnResults(View v){
        return (v.getId() == R.id.returnResults);
    }
}


