package com.ramon.testapplication;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setDetails(Context context, String image){
        imageView = itemView.findViewById(R.id.iv_item);
        Picasso.get().load(image).into(imageView);
        Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
        itemView.setAnimation(animation);
    }
}