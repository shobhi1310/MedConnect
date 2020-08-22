package com.example.medconnect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medconnect.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Intro1Fragment extends Fragment {
    ImageView introImage;
    TextView title;
    TextView description;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.intro1_fragment, container, false);

        introImage = view.findViewById(R.id.intro_img1);
        title = view.findViewById(R.id.intro_title1);
        description = view.findViewById(R.id.intro_description1);

        title.setText("Book Medicines");
        description.setText("Oh no need to hurry dude! You'll be fine, you just booked your medicine. Noone's gonna take it away.");
        introImage.setImageResource(R.drawable.img1);

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new Intro2Fragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.intro1_layout, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        return view;
    }
}
