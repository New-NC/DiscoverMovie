package io.newnc.discovermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.stream.IntStream;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.model.Movie;
import io.newnc.discovermovie.task.DownloadImageTask;
import io.newnc.discovermovie.task.TaskCallback;

public class MovieDescription extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    private SendButton sendButton;
    private ShareButton shareButton;

    private SharePhotoContent content = null;

    private TextView titleText;
    private ImageView coverImage;
    private ProgressBar progressBar;
    private TextView releaseDateText;
    private TextView voteAverageText;
    private TextView overviewText;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_movie);

        titleText = (TextView) findViewById(R.id.movie_Title);
        coverImage = (ImageView) findViewById(R.id.movie_cover);
        progressBar = (ProgressBar) findViewById(R.id.movie_cover_progress);
        releaseDateText = (TextView) findViewById(R.id.movie_release_date);
        voteAverageText = (TextView) findViewById(R.id.movie_vote_avg);
        overviewText = (TextView) findViewById(R.id.overview);

        sendButton = (SendButton) findViewById(R.id.send_movie_button);
        shareButton = (ShareButton) findViewById(R.id.share_movie_button);
        backButton = (Button) findViewById(R.id.returnResults);

        setClickListener();

        AppController.getInstance().loadDescription(this);

        /*
        prepareContent();
        prepareSendMessenger();
        prepareShareTimeline();
        */
    }

    private void setClickListener() {
        backButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
    }

    public void prepareContent() {
        if (content == null) {
            Bitmap bitmap = ((BitmapDrawable) coverImage.getDrawable()).getBitmap();
            SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
            content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .setShareHashtag(new ShareHashtag.Builder().setHashtag("#DiscoverMovieApp").build())
                    .build();
        }
    }

    private void prepareShareTimeline() {
        shareButton.setShareContent(content);
    }

    private void prepareSendMessenger() {
        CallbackManager callbackManager = CallbackManager.Factory.create();

        sendButton.setShareContent(content);
        sendButton.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                log("facebook:onSuccess" + result);

                Toast.makeText(MovieDescription.this, R.string.success_share_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                log("facebook:onCancel");

                Toast.makeText(MovieDescription.this, R.string.cancel_share_message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                error("facebook:onError", error);

                Toast.makeText(MovieDescription.this, R.string.error_share_message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startQuestionnaire(View view) {
        Intent myIntent = new Intent(view.getContext(), FirstQuestion.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void doSomething(Object o) {
        Movie movie = (Movie) o;

        titleText.setText(movie.getTitle());
        new DownloadImageTask(this, coverImage, progressBar).execute(movie.getCover_path());
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
    public boolean buttonShare(View v) { return (v.getId() == R.id.share_movie_button); }
    public boolean buttonSend(View v) { return (v.getId() == R.id.send_movie_button); }
}


