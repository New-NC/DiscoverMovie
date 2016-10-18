package newnc.github.io.discovermovie.view;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import newnc.github.io.discovermovie.R;
import newnc.github.io.discovermovie.controller.AppController;
import newnc.github.io.discovermovie.task.DownloadImageTask;
import newnc.github.io.discovermovie.task.TaskCallback;

public class Results extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    Button      buttonReturnHome;
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    ImageButton imageButton4;
    ImageButton imageButton5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setClickListener();

        imageButton1 = (ImageButton) findViewById(R.id.result1);
        imageButton2 = (ImageButton) findViewById(R.id.result2);
        imageButton3 = (ImageButton) findViewById(R.id.result3);
        imageButton4 = (ImageButton) findViewById(R.id.result4);
        imageButton5 = (ImageButton) findViewById(R.id.result5);

        AppController.getInstance().loadResult(this);
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

    @Override
    public void doSomething(Object o) {
        new DownloadImageTask(imageButton1).execute(((String[]) o)[0]);
        new DownloadImageTask(imageButton2).execute(((String[]) o)[1]);
        new DownloadImageTask(imageButton3).execute(((String[]) o)[2]);
        new DownloadImageTask(imageButton4).execute(((String[]) o)[3]);
        new DownloadImageTask(imageButton5).execute(((String[]) o)[4]);
    }
}

