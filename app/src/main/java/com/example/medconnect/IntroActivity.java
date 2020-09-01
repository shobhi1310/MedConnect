package com.example.medconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.medconnect.ui.main.Intro1Fragment;
import com.example.medconnect.ui.main.Intro2Fragment;
import com.example.medconnect.ui.main.LoginSigninFragment;
import com.example.medconnect.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class IntroActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    TabLayout tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_intro);

//        getSupportActionBar().hide();

        tabIndicator = findViewById(R.id.tab_indicator);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.screen_viewpager);
        setupViewPager(viewPager);

        tabIndicator.setupWithViewPager(viewPager);

//        DistanceCalculator dc = new DistanceCalculator(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Intro1Fragment(), "");
        adapter.addFragment(new Intro2Fragment(), "");
        adapter.addFragment(new LoginSigninFragment(), "");
        viewPager.setAdapter(adapter);
    }

//    public void addDotsIndicator() {
//        mDots = new TextView[2];
//        for (int i = 0; i < mDots.length; i++) {
//            mDots[i] = new TextView(this);
//            mDots[i].setText(Html.fromHtml("&#8336;"));
//            mDots[i]
//        }
//    }

}