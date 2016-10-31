package newnc.github.io.discovermovie.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import newnc.github.io.discovermovie.R;
import newnc.github.io.discovermovie.controller.AppController;
import newnc.github.io.discovermovie.task.DownloadImageTask;
import newnc.github.io.discovermovie.task.TaskCallback;

public class    SecondQuestion extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    ImageButton imageButtonAnimal;
    ProgressBar progressBarAnimal;
    ImageButton imageButtonTech;
    ProgressBar progressBarTech;
    ImageButton imageButtonPrincess;
    ProgressBar progressBarPrincess;
    ImageButton imageButtonAdventure;
    ProgressBar progressBarAdventure;
    Button      buttonNextQuestion;
    Button buttonReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);
        setClickListener();
        buttonNextQuestion.setVisibility(View.INVISIBLE);
        AppController.getInstance().loadCategories(this);
    }

    @Override
    public void onBackPressed() {
        backButtonMainActivity();
    }


    @Override
    public void onClick(View v) {

        if (buttonAdventure(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonAdventure);
            disableHighlightButton(imageButtonAnimal);
            disableHighlightButton(imageButtonPrincess);
            disableHighlightButton(imageButtonTech);

            AppController.getInstance().setCategorie(0);

            return;
        }

        if (buttonAnimal(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonAnimal);
            disableHighlightButton(imageButtonAdventure);
            disableHighlightButton(imageButtonPrincess);
            disableHighlightButton(imageButtonTech);

            AppController.getInstance().setCategorie(1);

            return;
        }

        if (buttonPrincess(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonPrincess);
            disableHighlightButton(imageButtonAdventure);
            disableHighlightButton(imageButtonAnimal);
            disableHighlightButton(imageButtonTech);

            AppController.getInstance().setCategorie(2);

            return;
        }

        if (buttonTech(v)) {
            buttonReturnHome.setVisibility(View.INVISIBLE);
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonTech);
            disableHighlightButton(imageButtonAdventure);
            disableHighlightButton(imageButtonAnimal);
            disableHighlightButton(imageButtonPrincess);

            AppController.getInstance().setCategorie(3);

            return;
        }

        if(buttonNextQuestion(v)){
            AppController.getInstance().loadResult(this);

            Intent myIntent = new Intent(v.getContext(), Results.class); /** Class name here */
            startActivityForResult(myIntent, 0);

            return;
        }

        if(buttonReturnHome(v)){
            backButtonMainActivity();
        }
    }

    /* AUXILIARY FUNCTIONS */

    //check if the button Adventure was clicked
    public boolean buttonAdventure(View v) {
        return (v.getId() == R.id.adventure);
    }

    //check if the button Animal was clicked
    public boolean buttonAnimal(View v) {
        return (v.getId() == R.id.animal);
    }

    //check if the button Princess was clicked
    public boolean buttonPrincess(View v) {
        return (v.getId() == R.id.princess);
    }

    //check if the button Tech was clicked
    public boolean buttonTech(View v) {
        return (v.getId() == R.id.tech);
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
        imageButtonAnimal = (ImageButton) findViewById(R.id.animal);
        imageButtonAnimal.setOnClickListener(this);
        progressBarAnimal = (ProgressBar) findViewById(R.id.animalProgress);
        imageButtonTech = (ImageButton) findViewById(R.id.tech);
        imageButtonTech.setOnClickListener(this);
        progressBarTech = (ProgressBar) findViewById(R.id.techProgress);
        imageButtonPrincess = (ImageButton) findViewById(R.id.princess);
        imageButtonPrincess.setOnClickListener(this);
        progressBarPrincess = (ProgressBar) findViewById(R.id.princessProgress);
        imageButtonAdventure = (ImageButton) findViewById(R.id.adventure);
        imageButtonAdventure.setOnClickListener(this);
        progressBarAdventure = (ProgressBar) findViewById(R.id.adventureProgress);
        buttonNextQuestion = (Button) findViewById(R.id.nextQuestion);
        buttonNextQuestion.setOnClickListener(this);
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }

    public void backButtonMainActivity() {
        Intent myIntent = new Intent(SecondQuestion.this, MainActivity.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }


    @Override
    public void doSomething(Object o) {
        Log.d("ABC1", "doSomething: o = " + o);
        Log.d("ABC2", "doSomething: string o = " + (String[]) o);
        Log.d("ABC3", "doSomething: tam o = " + ((String[]) o).length);
        Log.d("ABC3", "doSomething: o[0] = " + ((String[]) o)[0]);

        if (((String[]) o)[0] != null)
            new DownloadImageTask(imageButtonAdventure, progressBarAdventure).execute(((String[]) o)[0]);

        if (((String[]) o)[1] != null)
            new DownloadImageTask(imageButtonAnimal, progressBarAnimal).execute(((String[]) o)[1]);

        if (((String[]) o)[2] != null)
            new DownloadImageTask(imageButtonPrincess, progressBarPrincess).execute(((String[]) o)[2]);

        if (((String[]) o)[3] != null)
            new DownloadImageTask(imageButtonTech, progressBarTech).execute(((String[]) o)[3]);
        
    }
}
