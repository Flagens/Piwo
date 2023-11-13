package com.example.myapplication;

import android.animation.ObjectAnimator;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;
    private final List<beer> beersList;





    public MyAdapter(Context context, List<beer> beersList) {
        this.context = context;
        this.beersList = beersList;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.textView.setText(beersList.get(position).text);


        if (MainActivity.isBitmap == 1) {
            //holder.imageView.setImageBitmap(MainActivity.selectedBitmap);
            holder.imageView.setImageBitmap(scaleBitmap(MainActivity.selectedBitmap,900,500));
        } else {
            holder.imageView.setImageResource(beersList.get(position).image);
        }






        holder.pourGlass.setOnClickListener(v -> {
            Drawable imageViewDrawable = holder.imageView.getDrawable();
            Drawable glassDrawable = ContextCompat.getDrawable(context, R.drawable.glass);
            Drawable pustaDrawable = ContextCompat.getDrawable(context, R.drawable.pusta);

            if (imageViewDrawable != null) {
                if (imageViewDrawable.getConstantState() != null) {
                    if (imageViewDrawable.getConstantState().equals(Objects.requireNonNull(glassDrawable).getConstantState())) {
                        pour(holder.imageView, R.drawable.pusta);
                    } else if (imageViewDrawable.getConstantState().equals(Objects.requireNonNull(pustaDrawable).getConstantState()) ||
                            imageViewDrawable.getConstantState().equals(Objects.requireNonNull(glassDrawable).getConstantState())) {
                        Toast.makeText(context, "Pustej szklanki nie da sie napelnic ", Toast.LENGTH_SHORT).show();
                    } else {
                        pour(holder.imageView, R.drawable.glass);
                    }
                }
            } else {
                Toast.makeText(context, "brak image", Toast.LENGTH_SHORT).show();
            }
        });

        holder.drawBeer.setOnClickListener(v -> {
            Intent intent = new Intent(context, DrawingActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

    }



    @Override
    public int getItemCount() {
        return beersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView textView;
        private final Button pourGlass;
        private final Button drawBeer;



        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.beerImage);
            textView = view.findViewById(R.id.beerName);
            pourGlass = (Button) view.findViewById(R.id.pourGlass);
            drawBeer = (Button) view.findViewById(R.id.drawBeer);
        }
    }

    private void pour(final ImageView view, int img) {
        ObjectAnimator liftUpAnimation = ObjectAnimator.ofFloat(view, "translationY", -100f);
        liftUpAnimation.setDuration(1000);

        RotateAnimation rotate = new RotateAnimation(0, 140, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setFillAfter(true);

        new Handler().postDelayed(liftUpAnimation::start, 0);
        new Handler().postDelayed(() -> view.startAnimation(rotate), 2000);
        new Handler().postDelayed(() -> {
            view.clearAnimation();
            view.setImageResource(img);
        }, 5000);
    }

    public Bitmap scaleBitmap(Bitmap source, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

}
