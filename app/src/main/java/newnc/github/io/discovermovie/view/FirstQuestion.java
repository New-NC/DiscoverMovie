package newnc.github.io.discovermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import newnc.github.io.discovermovie.R;
import newnc.github.io.discovermovie.controller.AppController;
import newnc.github.io.discovermovie.task.DownloadImageTask;
import newnc.github.io.discovermovie.task.TaskCallback;

public class FirstQuestion extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    ImageButton imageButtonTopRated;
    ImageButton imageButtonNewest;
    Button      buttonNextQuestion;
    Button buttonReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);
        setClickListener();
        buttonNextQuestion.setVisibility(View.INVISIBLE);
        AppController.getInstance().loadCovers(this);
    }

    @Override
    public void onBackPressed() {
        backButtonMainActivity();
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

        if(buttonNextQuestion(v)){
            Intent myIntent = new Intent(v.getContext(), SecondQuestion.class); /** Class name here */
            startActivityForResult(myIntent, 0);
        }

        if(buttonReturnHome(v)){
            backButtonMainActivity();
        }

    }


    /* AUXILIARY FUNCTIONS */

    //check if the button Nemo was clicked
    public boolean buttonNemo(View v) {
        return (v.getId() == R.id.bestRated);
    }

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
    }

    //function to disable the highlight when another button is clicked
    public void disableHighlightButton(ImageButton imageButtonNotClicked) {
        imageButtonNotClicked.clearColorFilter();
    }


    private void setClickListener() {
        imageButtonTopRated = (ImageButton) findViewById(R.id.bestRated);
        imageButtonTopRated.setOnClickListener(this);
        imageButtonNewest = (ImageButton) findViewById(R.id.newest);
        imageButtonNewest.setOnClickListener(this);
        buttonNextQuestion = (Button) findViewById(R.id.nextQuestion);
        buttonNextQuestion.setOnClickListener(this);
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }

    public void backButtonMainActivity() {
        Intent myIntent = new Intent(FirstQuestion.this, MainActivity.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void doSomething(Object o) {
        new DownloadImageTask(imageButtonNewest).execute(((String[]) o)[0]);
        new DownloadImageTask(imageButtonTopRated).execute(((String[]) o)[1]);
    }
}


