package com.inks.popuptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                Intent intent = new Intent(this,PromptTestActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                 intent = new Intent(this,SelectTestActivity.class);
                startActivity(intent);
                break;

            case R.id.button3:
                intent = new Intent(this,PopupViewTestActivity.class);
                startActivity(intent);
                break;
        }
    }

}
