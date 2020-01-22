package geekspocket.farziengineers.com;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import geekspocket.farziengineers.com.geekspocket.R;


public class SecondScreen extends AppCompatActivity {

    private TextView keywordName, keywordDefination;
    private ImageView keywordExample;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        toolbar = findViewById(R.id.toolbar);

        MobileAds.initialize(this, "ca-app-pub-7652080645032592~1242400536");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("B2C8C4325EA18711D94B092972FD6D16").build();
        mAdView.loadAd(adRequest);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitleEnabled(false);

        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        keywordName = findViewById(R.id.keywordName);
        keywordDefination = findViewById(R.id.keywordDefination);
        keywordExample = findViewById(R.id.keywordExample);

        String keyword = getIntent().getExtras().getString("keyword");
        String defination = getIntent().getExtras().getString("Defination");
        String exampleImageURL = getIntent().getExtras().getString("example");

        toolbar.setTitle(keyword);

        keywordName.setText(keyword);
        keywordDefination.setText(defination);

       /* Glide.with(getApplicationContext())
                .load(exampleImageURL)
                .asBitmap()
                .fitCenter()
                .centerCrop()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(keywordExample);*/
        Picasso.get().load(exampleImageURL).fit()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .into(keywordExample);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(keywordExample);
        photoViewAttacher.update();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
