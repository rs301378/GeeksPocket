package geekspocket.farziengineers.com;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import geekspocket.farziengineers.com.geekspocket.R;

public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ImageView imageView;
    FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);
        appBarLayout = findViewById(R.id.app_bar_layout);
        viewPager = findViewById(R.id.view_pager);
        imageView = findViewById(R.id.info);

        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.progressBar);
        animationView.setAnimation("data.json");
        animationView.loop(true);
        animationView.playAnimation();

        MobileAds.initialize(this, "ca-app-pub-7652080645032592~1242400536");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("B2C8C4325EA18711D94B092972FD6D16").build();
        mAdView.loadAd(adRequest);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        animationView.setVisibility(View.VISIBLE);

        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new C(), "C");
        adapter.addFragment(new CPP(), "C++");
        adapter.addFragment(new Java(), "Java");
        adapter.addFragment(new PHP(), "Php");
        adapter.addFragment(new Python(), "Python");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        adapter.notifyDataSetChanged();
        animationView.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                showDiaglogAboutUs();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showDiaglogAboutUs() {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this)
                .setView(R.layout.alert_dialog)
                .setTitle("About Us")
                .setNegativeButton("close", null)
                .show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }
}
