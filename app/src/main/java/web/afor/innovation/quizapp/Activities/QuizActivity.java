package web.afor.innovation.quizapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.Models.Answer;
import web.afor.innovation.quizapp.R;

public class QuizActivity extends AppCompatActivity {


    TextView question_view;
    TextView player1_view;
    TextView player2_view;
    TextView player3_view;
    TextView player4_view;
    TextView score_view;
    RelativeLayout progress_view;
    ArrayList<Answer> answer;
    TextView username_view;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preferences.saveBoolean(false, "firstTime", getApplicationContext());
        setContentView(R.layout.activity_quiz);

        //Preferences.saveBoolean(false, "firstTime", );
        new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);
        question_view = (TextView) findViewById(R.id.question_view);
        player1_view = (TextView) findViewById(R.id.player1_view);
        player2_view = (TextView) findViewById(R.id.player2_view);
        player3_view = (TextView) findViewById(R.id.player3_view);
        player4_view = (TextView) findViewById(R.id.player4_view);
        score_view = (TextView) findViewById(R.id.score_view);
        progress_view = (RelativeLayout) findViewById(R.id.progress);
        username_view = (TextView) findViewById(R.id.username_view);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        score = sharedPref.getInt("score", 0);
        score_view.setText(String.valueOf(score));

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        username_view.setText(sharedPreferences.getString("username", ""));

        //Log.i("Receivedemail",email);
    }

    public void newQuestion() {
        android.os.Handler handler = new android.os.Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                QuizActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //RESET_ALL_VIEWS
                        Reset_All_View();
                        new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);
                        progress_view.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 2000);
    }

    public void Reset_All_View() {
        player1_view.setBackground(null);
        player2_view.setBackground(null);
        player3_view.setBackground(null);
        player4_view.setBackground(null);
        score_view.setText(String.valueOf(score));

    }

    public void save() {
        Context context = QuizActivity.this;
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d("sc", score + "");
        editor.putInt("score", score);
        editor.commit();
    }


    class DownloadQuestion extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            //Log.d("st",s);
            final JSONArray objArray;
            answer = new ArrayList<Answer>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String question = jsonObject.getString("question");
                boolean choice = jsonObject.getBoolean("multiple_choice");
                objArray = jsonObject.getJSONArray("answers");
                int answers_length = objArray.length();
                for (int i = 0; i < answers_length; i++) {

                    JSONObject object = objArray.getJSONObject(i);
                    String answer_text = object.getString("answer_text");
                    Boolean valid = object.getBoolean("correct");
                    answer.add(new Answer(answer_text, valid));
                    Log.d("Tag", "response " + answer_text + " ");

                }

                question_view.setText(question);
                if (answers_length > 0)
                    player1_view.setText(answer.get(0).getResponse());
                if (answers_length > 1)
                    player2_view.setText(answer.get(1).getResponse());
                if (answers_length > 2)
                    player3_view.setText(answer.get(2).getResponse());
                if (answers_length > 3)
                    player4_view.setText(answer.get(3).getResponse());


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progress_view.setVisibility(View.INVISIBLE);
            Log.d("Tag", "response " + s);
            super.onPostExecute(s);
        }
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}