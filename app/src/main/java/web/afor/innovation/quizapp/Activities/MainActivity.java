package web.afor.innovation.quizapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import web.afor.innovation.quizapp.Adapters.TagsAdapter;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.Models.Tags;
import web.afor.innovation.quizapp.R;
import web.afor.innovation.quizapp.Utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SimpleFacebook mSimpleFacebook;
    OnPublishListener onPublishListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        Preferences.saveBoolean(false, "firstTime", getApplicationContext());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        replaceFragment(0);

        TextView textView = (TextView) header.findViewById(R.id.name_tv);
        textView.setText(Preferences.loadString(getApplicationContext(), "name_user", ""));
        setupSdkFacebook();
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

    private void setupSdkFacebook() {
        mSimpleFacebook = SimpleFacebook.getInstance(MainActivity.this);
        onPublishListener = new OnPublishListener() {
            @Override
            public void onComplete(String postId) {
                Log.e("Sharing Facebook", "Published successfully...");
            }

        };
    }

    private void shareScore() {
        Feed feed = new Feed.Builder()
                .setMessage("Quiz App")
                .setName("My Score at this game")
                .setDescription("Mon score est 239")
                //.setPicture("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQM-9Szxyl-fTvAfQylko_AIkj-zcaH7nBj3GpOU_Kr0mAeNqyGjw")
                .setLink("https://play.google.com/store/apps/details?id=com.quizup.core&hl=fr")
                .build();

        mSimpleFacebook.publish(feed, true, onPublishListener);
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
        if (id == R.id.action_disconnect) {

            Preferences.saveBoolean(true, "firstTime", getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), Activity_login.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.action_share) {
            shareScore();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            replaceFragment(2);
        } else if (id == R.id.nav_gallery) {

            replaceFragment(5);

        } else if (id == R.id.nav_share) {

            replaceFragment(6);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(int position) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = Fragment.instantiate(getApplicationContext(), Constants.ARRAY_FRAGMENTS[position]);
        fragmentTransaction.replace(R.id.content_layout, fragment);
        fragmentTransaction.commit();
    }

    public void replaceFragmentWithBundle(String key, String value) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = Fragment.instantiate(getApplicationContext(), Constants.ARRAY_FRAGMENTS[3]);

        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_layout, fragment);
        fragmentTransaction.commit();

    }


}
