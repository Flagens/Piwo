package com.example.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import android.content.res.Resources;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;
import java.util.Objects;

import android.os.Handler;
import android.widget.Toast;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;

    private final List<beer> beersList;


    private final RecyclerView recyclerView;


    public MyAdapter(Context context, List<beer> beersList,RecyclerView recyclerView) {
        this.context = context;
        this.beersList = beersList;
        this.recyclerView = recyclerView;

    }


    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.textView.setText(beersList.get(position).text);
        holder.imageView.setImageResource(beersList.get(position).image);
        holder.pourGlass.setOnClickListener(v->{

            Drawable imageViewDrawable = holder.imageView.getDrawable();

            if (imageViewDrawable != null) {
                Drawable glassDrawable = ContextCompat.getDrawable(context, R.drawable.glass);
                Drawable pustaDrawable = ContextCompat.getDrawable(context, R.drawable.pusta);

                if (imageViewDrawable.getConstantState() != null) {
                    if (imageViewDrawable.getConstantState().equals(Objects.requireNonNull(glassDrawable).getConstantState())) {

                        pour(holder.imageView, R.drawable.pusta);
                    } else if (imageViewDrawable.getConstantState().equals(Objects.requireNonNull(pustaDrawable).getConstantState()) ||
                            imageViewDrawable.getConstantState().equals(Objects.requireNonNull(glassDrawable).getConstantState())) {

                        Toast.makeText(context, "Nie da sie", Toast.LENGTH_SHORT).show();
                    } else {

                        pour(holder.imageView, R.drawable.glass);
                    }
                }
            } else {
                Toast.makeText(context, "Nie ma image", Toast.LENGTH_SHORT).show();
            }









        });

    }

    private void pour(final ImageView view, int img) {
        ObjectAnimator liftUpAnimation = ObjectAnimator.ofFloat(view, "translationY", -100f);
        liftUpAnimation.setDuration(1000);

        RotateAnimation rotate = new RotateAnimation(0, 140, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                liftUpAnimation.start();
            }
        }, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                view.startAnimation(rotate);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                view.clearAnimation();
                view.setImageResource(img);

            }
        }, 5000);
    }











    @Override
    public int getItemCount() {
       return beersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;

        public Button pourGlass;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.beerImage);
            textView = view.findViewById(R.id.beerName);
            pourGlass = (Button) view.findViewById(R.id.pourGlass);

        }


    }

}
