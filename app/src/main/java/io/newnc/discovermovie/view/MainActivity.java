package io.newnc.discovermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.task.TaskCallback;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    // Facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    log("onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    log("onAuthStateChanged:signed_out");
                }
            }
        };

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.button_facebookLogin);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                log("facebook:onSuccess" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                log("facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                error("facebook:onError", error);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        log("handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                log("signInWithCredential:onComplete:" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    error("signInWithCredential", task.getException());

                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
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

    private static final String CATEG = "MainActivity";
    private void log(String message) {
        Log.v(CATEG, message);
    }
    private void error(String message, Exception exception) { Log.v(CATEG, message, exception); }
}


