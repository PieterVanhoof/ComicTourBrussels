package ehb.be.comictourbrussels;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.io.IOException;

import ehb.be.comictourbrussels.Utils.ComicHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private ComicHandler nComicHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nComicHandler = new ComicHandler(getApplicationContext());

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().add(R.id.main_container, MapFragment.newInstance()).commit();

       downloadData();

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, MapFragment.newInstance())
                    .commit();

        } else if (id == R.id.nav_list) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, ListFragment.newInstance())
                    .commit();



        } else if (id == R.id.nav_about) {

            //about
            AboutFragment aboutFragment = new AboutFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, aboutFragment)
                    .commit();
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void downloadData(){
        Thread backThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("https://opendata.brussel.be/api/records/1.0/search/?dataset=comic-book-route&rows=80")
                            .get().build();

                    Response response = client.newCall(request).execute();

                    String responseBodyText = response.body() != null ? response.body().string() : null;

                    Message msg = new Message();
                    msg.obj = responseBodyText;
                    nComicHandler.sendMessage(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backThread.start();
    }
}
