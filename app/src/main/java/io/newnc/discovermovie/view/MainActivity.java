package io.newnc.discovermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import io.newnc.discovermovie.R;
import io.newnc.discovermovie.task.TaskCallback;

public class MainActivity extends AppCompatActivity implements TaskCallback {

    // Facebook
    private CallbackManager callbackManager;
    private LoginButton loginButton;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // Drawer Layout
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    // Toolbar
    private Toolbar mToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        AppEventsLogger.activateApp(this);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

        NavigationView navigationView = (NavigationView) mDrawerLayout.getChildAt(1);
        final View headerLayout = navigationView.getHeaderView(0);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) headerLayout.findViewById(R.id.login_button);
        final TextView nameText = (TextView) headerLayout.findViewById(R.id.name);
        final TextView emailText = (TextView) headerLayout.findViewById(R.id.email);
        final CircleImageView circleView = (CircleImageView) headerLayout.findViewById(R.id.circleView);

        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken token = loginResult.getAccessToken();
                log("Loged with success to facebook!");
                log("Token: " + token.getToken());
                log("UserId: " + token.getUserId());

                RequestCreator rc = Picasso.with(getApplicationContext()).load("https://graph.facebook.com/" + token.getUserId() + "/picture");
                rc.into(circleView);

                GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            nameText.setText(object.getString("name"));
                            emailText.setText(object.getString("email"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();

                AccessTokenTracker tracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
                            circleView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                            nameText.setText("");
                            emailText.setText("");
                        }
                    }
                };
            }

            @Override
            public void onCancel() {
                log("cancel");
            }

            @Override
            public void onError(FacebookException error) {
                log(error.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

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


