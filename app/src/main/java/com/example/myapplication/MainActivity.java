package com.example.myapplication;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] jasnebeers = {
            "jasne1",
            "jasne2",
            "jasne3",
            "jasne4"

    };

    String[] ciemnebeers = {
            "ciemne1",
            "ciemne2",
            "ciemne3",
            "ciemne4"
    };

    String[] burszytynowebeers = {
            "bursztynowe1",
            "bursztynowe2",
            "bursztynowe3",
            "bursztynowe4"
    };

    static Bitmap selectedBitmap;
    static int isBitmap = 0;
    boolean isTrue = false;

    int lech = R.drawable.lech;
    int pan = R.drawable.pan;
    int zywiec = R.drawable.zywiec;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = findViewById(R.id.spinner);
        
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.beer_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button selectBeer = findViewById(R.id.selectBeer);


        selectBeer.setOnClickListener(v -> {
            List<beer> beersList = new ArrayList<>();
            int selectedSpinnerValue = spinner.getSelectedItemPosition();

            List<String> selectedBeers = new ArrayList<>();
            int selectedImageResource = 0;


            switch (selectedSpinnerValue) {
                case 0:
                    selectedBeers = Arrays.asList(jasnebeers);
                    selectedImageResource = lech;
                    break;
                case 1:
                    selectedBeers = Arrays.asList(ciemnebeers);
                    selectedImageResource = pan;
                    break;
                case 2:
                    selectedBeers = Arrays.asList(burszytynowebeers);
                    selectedImageResource = zywiec;
                default:
                    break;
            }


            for (String beer : selectedBeers) {
                        beersList.add(new beer(beer, selectedImageResource));
                    }


            recyclerView.setAdapter(new MyAdapter(getApplicationContext(), beersList));
        });

        Button spinButton = findViewById(R.id.spin);

        Button spinLoopButton = findViewById(R.id.spinnLoop);

        spinButton.setOnClickListener(v -> {

                spin(true,recyclerView,0,360);
        });

        spinLoopButton.setOnClickListener(v -> {
            isTrue = !isTrue;
            spin(isTrue,recyclerView,-1,360);

        });


    }




    public void spin(boolean isTrue,RecyclerView view,int amount,int degrees) {
        if (isTrue) {
            RotateAnimation rotate = new RotateAnimation(0, degrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(5000);
            rotate.setInterpolator(new LinearInterpolator());
            if(amount == -1) {
                rotate.setRepeatCount(Animation.INFINITE);
            } else {
                rotate.setRepeatCount(amount);
            }

            view.startAnimation(rotate);
        } else {
            view.clearAnimation();
        }
    }

    public static void setBitmap(Bitmap bitmap) {
        isBitmap = 1;
        selectedBitmap = bitmap;

    }



}






