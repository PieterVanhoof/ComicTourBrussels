package ehb.be.comictourbrussels;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import ehb.be.comictourbrussels.Utils.ComicHandler;
import ehb.be.comictourbrussels.Utils.WCHandler;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    private ComicHandler nComicHandler;
    private WCHandler nWCHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        nWCHandler = new WCHandler(getApplicationContext());
        nComicHandler = new ComicHandler(getApplicationContext());

        TextView txt = findViewById(R.id.txt);
        ImageView img = findViewById(R.id.img);
        downloadData();
        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,
                R.anim.myanim);
        img.startAnimation(animation);
        txt.startAnimation(animation);

        Handler handler = new Handler();
        int timeout = 3000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, timeout);
    }

    private void downloadData(){
        //comic thread
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
                    Toast.makeText(getApplicationContext(), "You are not connected to the internet, please restart the application while connected.",Toast.LENGTH_LONG).show();
                }

            }
        });
        //WC's backthread
        Thread wcBackThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder().url("https://opendata.brussel.be/api/records/1.0/search/?dataset=bruxelles_urinoirs_publics&rows=100")
                            .get().build();
                    Response response = client.newCall(request).execute();

                    String responseBodyText = response.body() != null ? response.body().string() :null;

                    Message msg = new Message();
                    msg.obj = responseBodyText;
                    nWCHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backThread.start();
        wcBackThread.start();
    }
}
