package io.newnc.discovermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.task.TaskCallback;

public class MovieDescription extends AppCompatActivity implements TaskCallback {

    private ImageView coverMovie;
    private SendButton sendButton;
    private ShareButton shareButton;

    private SharePhotoContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coverMovie = (ImageView) findViewById(R.id.movie_cover);

        prepareContent();
        prepareSendMessenger();
        prepareShareTimeline();
    }

    private void prepareContent() {
        Bitmap bitmap = ((BitmapDrawable) coverMovie.getDrawable()).getBitmap();
        SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap).build();
        content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .setShareHashtag(new ShareHashtag.Builder().setHashtag("#DiscoverMovieApp").build())
                .build();
    }

    private void prepareShareTimeline() {
        shareButton = (ShareButton) findViewById(R.id.share_movie_button);
        shareButton.setShareContent(content);
    }

    private void prepareSendMessenger() {
        CallbackManager callbackManager = CallbackManager.Factory.create();

        sendButton = (SendButton) findViewById(R.id.send_movie_button);
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
        log("Something happens");
    }

    private static final String CATEG = "MovieDescription";
    private void log(String message) {
        Log.v(CATEG, message);
    }
    private void error(String message, Exception exception) { Log.v(CATEG, message, exception); }
}


