package io.newnc.discovermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.task.TaskCallback;

public class MovieDescription extends AppCompatActivity implements TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}


