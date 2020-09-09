package com.example.medconnect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medconnect.LoginActivity;
import com.example.medconnect.R;
import com.example.medconnect.SignUp;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginSigninFragment extends Fragment {

    ImageView introImage;
    TextView title;
    TextView description;
    Button login;
    Button signup;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_signin_fragment, container, false);

        introImage = view.findViewById(R.id.intro_img3);
        title = view.findViewById(R.id.intro_title3);
        description = view.findViewById(R.id.intro_description3);
        login = view.findViewById(R.id.login_btn);
        signup = view.findViewById(R.id.signin_btn);

        title.setText("Join Us");
        description.setText("A friend in need is the friend indeed, and we are those friends of yours. Be a part of this, you won't regret it.");
        introImage.setImageResource(R.drawable.img3);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignUp.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        signup = (Button)getActivity().findViewById(R.id.signin_btn);
//        signup.setOnClickListener((View.OnClickListener)this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        Intent intent = new Intent(getActivity(), SignUp.class);
//        startActivity(intent);
//    }
}
