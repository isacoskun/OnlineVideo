package onlinevideo.app;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference mDatabase;

    VideoView PromotionVideo;
    ProgressDialog pd;
    ImageView PromotionVideoLike,PlayButtonCircleSoft,PromotionPlayButton;

    LottieAnimationView LikeAnimationButton;
    Animation PlayButtonCircleAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** TEST */
        PromotionVideoLike = (ImageView)findViewById(R.id.PromotionVideoLike);
        PlayButtonCircleSoft = (ImageView)findViewById(R.id.PlayButtonCircleSoft);
        PromotionPlayButton = (ImageView)findViewById(R.id.PromotionPlayButton);
        PromotionVideo = (VideoView)findViewById(R.id.PromotionVideo);
        LikeAnimationButton = (LottieAnimationView) findViewById(R.id.LottieLikeButton);
        PlayButtonCircleAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.playbuttoncircleanim);


        PlayButtonCircleSoft.startAnimation(PlayButtonCircleAnim);

        PromotionPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PromotionVideo.start();
                PromotionPlayButton.setVisibility(View.INVISIBLE);
                PlayButtonCircleSoft.setVisibility(View.INVISIBLE);
                PlayButtonCircleSoft.clearAnimation();
            }
        });

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Buffering video please wait...");
        pd.show();

        PromotionVideoLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PromotionVideoLike.setVisibility(View.INVISIBLE);
                LikeAnimationButton.setVisibility(View.VISIBLE);
                LikeAnimationButton.playAnimation();

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("test");

        mDatabase.child("url").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);
                Uri uri = Uri.parse(url);
                PromotionVideo.setVideoURI(uri);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        PromotionVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //close the progress dialog when buffering is done
                pd.dismiss();
            }
        });

        /** TEST FINISH ------------------------------------------------------------------*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
