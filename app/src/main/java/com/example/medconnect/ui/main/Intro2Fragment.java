package com.example.medconnect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.medconnect.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Intro2Fragment extends Fragment {
    ImageView introImage;
    TextView title;
    TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro2_fragment, container, false);

        introImage = view.findViewById(R.id.intro_img2);
        title = view.findViewById(R.id.intro_title2);
        description = view.findViewById(R.id.intro_description2);

        title.setText("Follow the Path");
        description.setText("You have not seen the shop! No problem my friend, we got you a map, trace the path to your cure and get well soon.");
//        introImage.setImageResource(R.drawable.img2);
        Glide.with(this).load(getResources().getDrawable(R.drawable.img2)).into(introImage);
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return view;
    }
}
