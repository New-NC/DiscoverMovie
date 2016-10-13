package newnc.github.io.discovermovie.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import newnc.github.io.discovermovie.R;

import static newnc.github.io.discovermovie.R.drawable.nemo;
import static newnc.github.io.discovermovie.R.drawable.pets;

public class FirstQuestion extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageButtonNemo;
    ImageButton imageButtonPets;
    Button      buttonNextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);
        setClickListener();
        buttonNextQuestion.setVisibility(View.INVISIBLE);
    }



    /*if(imageButtonNemo.getColorFilter().equals(0x77000000)){
                imageButtonNemo.clearColorFilter();
            }*/
    @Override
    public void onClick(View v) {
        if (buttonNemo(v)) {
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonNemo);
            disableHighlightButton(imageButtonPets);
        }

        if (buttonPets(v)) {
            buttonNextQuestion.setVisibility(View.VISIBLE);

            highlightButton(imageButtonPets);
            disableHighlightButton(imageButtonNemo);
        }

        if(buttonNextQuestion(v)){
            Intent myIntent = new Intent(v.getContext(), SecondQuestion.class); /** Class name here */
            startActivityForResult(myIntent, 0);
        }

    }



    /* AUXILIARY FUNCTIONS */

    //check if the button Nemo was clicked
    public boolean buttonNemo(View v) {
        if (v.getId() == R.id.nemo) {
            return true;
        } else
            return false;
    }

    //check if the button Pets was clicked
    public boolean buttonPets(View v) {
        if (v.getId() == R.id.pets) {
            return true;
        } else
            return false;
    }

    public boolean buttonNextQuestion(View v){
        if (v.getId() == R.id.nextQuestion){
            return true;
        } else
            return false;
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
        imageButtonNemo = (ImageButton) findViewById(R.id.nemo);
        imageButtonNemo.setOnClickListener(this);
        imageButtonPets = (ImageButton) findViewById(R.id.pets);
        imageButtonPets.setOnClickListener(this);
        buttonNextQuestion = (Button) findViewById(R.id.nextQuestion);
        buttonNextQuestion.setOnClickListener(this);
    }
}


