package io.newnc.discovermovie.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.task.DownloadImageTask;
import io.newnc.discovermovie.task.TaskCallback;

public class FirstQuestion extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    ImageButton imageButtonTopRated;
    ProgressBar progressBarTopRated;
    ImageButton imageButtonNewest;
    ProgressBar progressBarNewest;
    Button      buttonNextQuestion;
    Button      buttonReturnHome;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        setClickListener();
        buttonNextQuestion.setVisibility(View.INVISIBLE);
        AppController.getInstance().loadCovers(this);
    }



    /*if(imageButtonTopRated.getColorFilter().equals(0x77000000)){
                imageButtonTopRated.clearColorFilter();
            }*/
    @Override
    public void onClick(View v) {
        if (buttonNemo(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonTopRated);
            disableHighlightButton(imageButtonNewest);
        }

        if (buttonPets(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonNewest);
            disableHighlightButton(imageButtonTopRated);
        }

        if (buttonNextQuestion(v)){
            Intent myIntent = new Intent(v.getContext(), SecondQuestion.class); /** Class name here */
            startActivityForResult(myIntent, 0);
            finish();
        }

        if (buttonReturnHome(v)){
            finish();
        }

    }


    /* AUXILIARY FUNCTIONS */

    //check if the button Nemo was clicked
    public boolean buttonNemo(View v) { return (v.getId() == R.id.bestRated); }

    //check if the button Pets was clicked
    public boolean buttonPets(View v) {
        return (v.getId() == R.id.newest);
    }

    public boolean buttonNextQuestion(View v){
        return (v.getId() == R.id.nextQuestion);
    }

    public boolean buttonReturnHome(View v){
        return (v.getId() == R.id.returnHome);
    }

    //function to highlight a button when clicked
    public void highlightButton(ImageButton imageButtonClicked) {
        imageButtonClicked.setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);

        if (buttonNemo(imageButtonClicked))
            AppController.getInstance().setCover(1);
        else if (buttonPets(imageButtonClicked))
            AppController.getInstance().setCover(0);
    }

    //function to disable the highlight when another button is clicked
    public void disableHighlightButton(ImageButton imageButtonNotClicked) {
        imageButtonNotClicked.clearColorFilter();
    }


    private void setClickListener() {
        imageButtonTopRated = (ImageButton) findViewById(R.id.bestRated);
        imageButtonTopRated.setOnClickListener(this);
        progressBarTopRated = (ProgressBar) findViewById(R.id.bestRatedProgress);
        imageButtonNewest = (ImageButton) findViewById(R.id.newest);
        imageButtonNewest.setOnClickListener(this);
        progressBarNewest = (ProgressBar) findViewById(R.id.newestProgress);
        buttonNextQuestion = (Button) findViewById(R.id.nextQuestion);
        buttonNextQuestion.setOnClickListener(this);
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }

    @Override
    public void doSomething(Object o) {
        new DownloadImageTask(this, imageButtonNewest, progressBarNewest).execute(((String[] ) o)[0]);
        new DownloadImageTask(this, imageButtonTopRated, progressBarTopRated).execute(((String[] ) o)[1]);
    }

}
