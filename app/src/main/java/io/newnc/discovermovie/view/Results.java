package io.newnc.discovermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import io.newnc.discovermovie.R;
import io.newnc.discovermovie.controller.AppController;
import io.newnc.discovermovie.model.Movie;
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

    String[] moviesIds = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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
        buttonReturnHome = (Button) findViewById(R.id.returnHome);

        setClickListener();

        AppController.getInstance().loadResult(this);
    }

    public void onClick(View v) {
        int id = whichImageButton(v);

        if (id > 0) {
            AppController.getInstance().setMovieId(moviesIds[id-1]);

            Intent intent = new Intent(getApplicationContext(), MovieDescription.class);
            startActivityForResult(intent, 0);
        } else if (buttonReturnHome(v))
            finish();
    }

    /* AUXILIARY FUNCTIONS */
    public boolean buttonReturnHome(View v){
        return (v.getId() == R.id.returnHome);
    }

    public int whichImageButton(View v) {
        if (v.getId() == R.id.result1) return 1;
        if (v.getId() == R.id.result2) return 2;
        if (v.getId() == R.id.result3) return 3;
        if (v.getId() == R.id.result4) return 4;
        if (v.getId() == R.id.result5) return 5;

        return 0;
    }

    private void setClickListener() {
        buttonReturnHome.setOnClickListener(this);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
    }

    @Override
    public void doSomething(Object o) {
        String[] moviesCoversAndIds = (String[]) o;

        new DownloadImageTask(this, imageButton1, progressBar1).execute(moviesCoversAndIds[0]);
        moviesIds[0] = moviesCoversAndIds[1];
        new DownloadImageTask(this, imageButton2, progressBar2).execute(moviesCoversAndIds[2]);
        moviesIds[1] = moviesCoversAndIds[3];
        new DownloadImageTask(this, imageButton3, progressBar3).execute(moviesCoversAndIds[4]);
        moviesIds[2] = moviesCoversAndIds[5];
        new DownloadImageTask(this, imageButton4, progressBar4).execute(moviesCoversAndIds[6]);
        moviesIds[3] = moviesCoversAndIds[6];
        new DownloadImageTask(this, imageButton5, progressBar5).execute(moviesCoversAndIds[8]);
        moviesIds[4] = moviesCoversAndIds[7];

    }

}
