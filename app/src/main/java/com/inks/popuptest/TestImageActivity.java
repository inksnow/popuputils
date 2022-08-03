package com.inks.popuptest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;

import com.inks.inkslibrary.view.ImagePreviewView;

public class TestImageActivity extends AppCompatActivity {
    ImagePreviewView imagePreviewView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        imagePreviewView = findViewById(R.id.imagePreviewView);


        String path = Environment.getExternalStorageDirectory()+"/立体巡护/无人机/照片/DJI_0001_1655970946000_8266360.jpg";
        imagePreviewView.setBitmap(BitmapFactory.decodeFile(path));
    }
}