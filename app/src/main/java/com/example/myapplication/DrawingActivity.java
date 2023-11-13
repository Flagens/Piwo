package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.widget.Button;


import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class DrawingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);


        DrawingView drawingView = findViewById(R.id.drawingView);
        Button saveBeer = findViewById(R.id.saveDrawing);



        saveBeer.setOnClickListener(v-> {
            Bitmap bitmap = drawingView.saveDrawing(drawingView);

            MainActivity.setBitmap(bitmap);

            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);

        });





    }

}