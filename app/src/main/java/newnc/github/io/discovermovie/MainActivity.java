package newnc.github.io.discovermovie;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startQuestionnaire(View view) {
        Intent myIntent = new Intent(view.getContext(), FirstQuestion.class); /** Class name here */
        startActivityForResult(myIntent, 0);
    }
}


