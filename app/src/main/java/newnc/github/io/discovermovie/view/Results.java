package newnc.github.io.discovermovie.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import newnc.github.io.discovermovie.R;

public class Results extends AppCompatActivity implements View.OnClickListener {

    Button      buttonReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setClickListener();
    }

    @Override
    public void onBackPressed() {
        backButtonMainActivity();
        return;
    }

    public void onClick(View v) {
        if(buttonReturnHome(v)){
            Intent myIntent = new Intent(v.getContext(), MainActivity.class); /** Class name here */
            startActivityForResult(myIntent, 0);
        }
    }

    /* AUXILIARY FUNCTIONS */
    public boolean buttonReturnHome(View v){
        if (v.getId() == R.id.returnHome){
            return true;
        } else
            return false;
    }

    private void setClickListener() {
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }

    public void backButtonMainActivity() {
        Intent myIntent = new Intent(Results.this, MainActivity.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }
}

