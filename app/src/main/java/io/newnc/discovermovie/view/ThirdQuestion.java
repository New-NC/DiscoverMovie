package io.newnc.discovermovie.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.task.DownloadImageTask;
import io.newnc.discovermovie.task.TaskCallback;

/**
 * Created by carolinapascalecampos on 08/11/16.
 */

public class ThirdQuestion extends AppCompatActivity implements View.OnClickListener, TaskCallback {
    ImageButton imageButtonDisney;
    ProgressBar progressBarDisney;
    ImageButton imageButtonPixar;
    ProgressBar progressBarPixar;
    ImageButton imageButtonDreamworks;
    ProgressBar progressBarDreamworks;
    ImageButton imageButtonGhibili;
    ProgressBar progressBarGhibili;
    Button buttonNextQuestion;
    Button buttonReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_question);
        setClickListener();
        buttonNextQuestion.setVisibility(View.INVISIBLE);
        AppController.getInstance().loadCompanies(this);
    }

    @Override
    public void onClick(View v) {

        if (buttonDisney(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonDisney);
            disableHighlightButton(imageButtonPixar);
            disableHighlightButton(imageButtonDreamworks);
            disableHighlightButton(imageButtonGhibili);

            AppController.getInstance().setCompany(R.integer.disney);

            return;
        }

        if (buttonPixar(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonPixar);
            disableHighlightButton(imageButtonGhibili);
            disableHighlightButton(imageButtonDreamworks);
            disableHighlightButton(imageButtonDisney);

            AppController.getInstance().setCompany(R.integer.pixar);

            return;
        }

        if (buttonDreamworks(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonDreamworks);
            disableHighlightButton(imageButtonDisney);
            disableHighlightButton(imageButtonGhibili);
            disableHighlightButton(imageButtonPixar);

            AppController.getInstance().setCompany(R.integer.dreamworks);

            return;
        }

        if (buttonGhibili(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonGhibili);
            disableHighlightButton(imageButtonPixar);
            disableHighlightButton(imageButtonDisney);
            disableHighlightButton(imageButtonDreamworks);

            AppController.getInstance().setCompany(R.integer.ghibili);

            return;
        }

        if(buttonNextQuestion(v)){
            AppController.getInstance().loadResult(this);

            Intent myIntent = new Intent(v.getContext(), Results.class); /** Class name here */
            startActivityForResult(myIntent, 0);

            finish();
        }

        if(buttonReturnHome(v)){
            finish();
        }
    }

    /* AUXILIARY FUNCTIONS */

    //check if the button Adventure was clicked
    public boolean buttonDisney(View v) {
        return (v.getId() == R.id.disney);
    }

    //check if the button Animal was clicked
    public boolean buttonPixar(View v) {
        return (v.getId() == R.id.pixar);
    }

    //check if the button Princess was clicked
    public boolean buttonDreamworks(View v) {
        return (v.getId() == R.id.dreamworks);
    }

    //check if the button Tech was clicked
    public boolean buttonGhibili(View v) {
        return (v.getId() == R.id.ghibili);
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
    }

    //function to disable the highlight when another button is clicked
    public void disableHighlightButton(ImageButton imageButtonNotClicked) {
        imageButtonNotClicked.clearColorFilter();
    }


    private void setClickListener() {
        imageButtonDisney = (ImageButton) findViewById(R.id.disney);
        imageButtonDisney.setOnClickListener(this);
        progressBarDisney = (ProgressBar) findViewById(R.id.disneyProgress);
        imageButtonPixar = (ImageButton) findViewById(R.id.pixar);
        imageButtonPixar.setOnClickListener(this);
        progressBarPixar = (ProgressBar) findViewById(R.id.pixarProgress);
        imageButtonDreamworks = (ImageButton) findViewById(R.id.dreamworks);
        imageButtonDreamworks.setOnClickListener(this);
        progressBarDreamworks = (ProgressBar) findViewById(R.id.dreamworksProgress);
        imageButtonGhibili = (ImageButton) findViewById(R.id.ghibili);
        imageButtonGhibili.setOnClickListener(this);
        progressBarGhibili = (ProgressBar) findViewById(R.id.ghibiliProgress);
        buttonNextQuestion = (Button) findViewById(R.id.nextQuestion);
        buttonNextQuestion.setOnClickListener(this);
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }


    @Override
    public void doSomething(Object o) {
        if (((String[]) o)[0] != null)
            new DownloadImageTask(this, imageButtonDisney, progressBarDisney).execute(((String[]) o)[0]);

        if (((String[]) o)[1] != null)
            new DownloadImageTask(this, imageButtonPixar, progressBarPixar).execute(((String[]) o)[1]);

        if (((String[]) o)[2] != null)
            new DownloadImageTask(this, imageButtonDreamworks, progressBarDreamworks).execute(((String[]) o)[2]);

        if (((String[]) o)[3] != null)
            new DownloadImageTask(this, imageButtonGhibili, progressBarGhibili).execute(((String[]) o)[3]);

    }
}


