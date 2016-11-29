package io.newnc.discovermovie.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.task.DownloadImageTask;
import io.newnc.discovermovie.task.TaskCallback;

public class Results extends AppCompatActivity implements View.OnClickListener, TaskCallback {

    Button buttonReturnHome;

    ImageButton imageButton1;
    ProgressBar progressBar1;

    ImageButton imageButton2;
    ProgressBar progressBar2;

    ImageButton imageButton3;
    ProgressBar progressBar3;

    ImageButton imageButton4;
    ProgressBar progressBar4;

    ImageButton imageButton5;
    ProgressBar progressBar5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setClickListener();

        imageButton1 = (ImageButton) findViewById(R.id.result1);
        progressBar1 = (ProgressBar) findViewById(R.id.result1Progress);
        imageButton2 = (ImageButton) findViewById(R.id.result2);
        progressBar2 = (ProgressBar) findViewById(R.id.result2Progress);
        imageButton3 = (ImageButton) findViewById(R.id.result3);
        progressBar3 = (ProgressBar) findViewById(R.id.result3Progress);
        imageButton4 = (ImageButton) findViewById(R.id.result4);
        progressBar4 = (ProgressBar) findViewById(R.id.result4Progress);
        imageButton5 = (ImageButton) findViewById(R.id.result5);
        progressBar5 = (ProgressBar) findViewById(R.id.result5Progress);

        AppController.getInstance().loadResult(this);
    }

    public void onClick(View v) {
        if(buttonReturnHome(v)){
            finish();
        }
    }

    /* AUXILIARY FUNCTIONS */
    public boolean buttonReturnHome(View v){
        return (v.getId() == R.id.returnHome);
    }

    private void setClickListener() {
        buttonReturnHome = (Button) findViewById(R.id.returnHome);
        buttonReturnHome.setOnClickListener(this);
    }

    @Override
    public void doSomething(Object o) {
        new DownloadImageTask(this, imageButton1, progressBar1).execute(((String[]) o)[0]);
        new DownloadImageTask(this, imageButton2, progressBar2).execute(((String[]) o)[1]);
        new DownloadImageTask(this, imageButton3, progressBar3).execute(((String[]) o)[2]);
        new DownloadImageTask(this, imageButton4, progressBar4).execute(((String[]) o)[3]);
        new DownloadImageTask(this, imageButton5, progressBar5).execute(((String[]) o)[4]);
    }

}