package com.example.tarea3_convert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();
    }


    public void setUp(){


        final Button mBtnSubmit = (Button) findViewById(R.id.btnSubmit);


        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               goToSecond();
               // we cannot call the explicit intent here, that's why i create the go method.
            }
        });
    }

    public void goToSecond(){// method to call the second intent Activity.

        Intent intent = new Intent(this,Convert_Activity.class);

        startActivity(intent);
    }
}
