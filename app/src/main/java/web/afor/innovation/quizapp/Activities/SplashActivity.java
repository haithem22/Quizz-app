package web.afor.innovation.quizapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent;
                if (Preferences.loadBoolean(SplashActivity.this, "firstTime", true)) {
                    intent = new Intent(SplashActivity.this, SignUpActivity.class);

                } else {

                    intent = new Intent(SplashActivity.this, MainActivity.class);

                }
                startActivity(intent);
                finish();


            }
        }, Constants.Splash_DELAY_VALUE);
    }
}
