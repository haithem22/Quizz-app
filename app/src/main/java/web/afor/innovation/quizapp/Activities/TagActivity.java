package web.afor.innovation.quizapp.Activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.Models.Answer;
import web.afor.innovation.quizapp.R;


public class TagActivity extends AppCompatActivity {

    private TextView question_view;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private TextView textViewAnswer4;
    private TextView score_view;
    private RelativeLayout progressBar;
    private ArrayList<Answer> answerList;
    private boolean timerRunning = false;
    private int score;
    private TextView username_view;
    private String strtext;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_open_scale,
                R.anim.activity_close_translate);


    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        setupToolbar();
        //getData();


        Preferences.loadInt(TagActivity.this, "key", 0);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("tag")) {
            strtext = bundle.getString("tag");
            Log.d("testBundle", strtext);
        }
        if (strtext.equalsIgnoreCase("general")) {

            new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);

        } else {
            String url = Constants.BASE_URL + Constants.URL_QUESTION_BY_TAG + strtext;
            new DownloadQuestion().execute(url);
            Log.i("url", url);

        }
        initViews();

        //generateToken();


    }

    /*public String generateToken() {
        final String token = "e8ffd0938fc5ada4c2232ac4c8929a33ce68eeee";
        SecureRandom rnd = new SecureRandom();
        int len = token.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(token.charAt(rnd.nextInt(token.length())));
        Log.d("GeneratedToken", sb.toString());
        return sb.toString();

    }*/

    private void initViews() {
        username_view = (TextView) findViewById(R.id.username_view);
        score = Preferences.loadInt(this, "score", 0);
        progressBar = (RelativeLayout) findViewById(R.id.progress);
        question_view = (TextView) findViewById(R.id.question_view);
        textViewAnswer1 = (TextView) findViewById(R.id.player1_view);
        verifyAnswer(textViewAnswer1, 0);
        textViewAnswer2 = (TextView) findViewById(R.id.player2_view);
        verifyAnswer(textViewAnswer2, 1);
        textViewAnswer3 = (TextView) findViewById(R.id.player3_view);
        verifyAnswer(textViewAnswer3, 2);
        textViewAnswer4 = (TextView) findViewById(R.id.player4_view);
        verifyAnswer(textViewAnswer4, 3);
        score_view = (TextView) findViewById((R.id.score_view));
        score_view.setText(String.valueOf(score));
        Log.d("ssss", "InTagActivity");
        username_view.setText(Preferences.loadString(this, "name_user", ""));
        //textViewScore = (TextView) findViewById(R.id.totalScore);
    }

    private void verifyAnswer(final TextView textView, final int i) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerList.size() > i && !timerRunning) {
                    if (answerList.get(i).isValidAnswer()) {
                        score += Constants.INC_VALUE;
                        Preferences.saveInt(TagActivity.this, "key", score);
                        textView.setBackground(getResources().getDrawable(R.drawable.back));
                        //textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.roundedgreen));
                        newQuestion();
                    } else {
                        score += Constants.DEC_VALUE;
                        Preferences.saveInt(TagActivity.this, "key", score);
                        textView.setBackground(getResources().getDrawable(R.drawable.falseanswer));
                    }
                    score_view.setText(String.valueOf(score));
                    answerList.get(i).setValidAnswer(true);
                }
            }
        });
    }

    private void newQuestion() {

        timerRunning = true;
        progressBar.setVisibility(View.VISIBLE);
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() { //parce qu'il faut que ça s'execute sur le main thread de la UI
                    @Override
                    public void run() {

                        timerRunning = false;
                        if (strtext.equalsIgnoreCase("general")) {

                            new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);

                        } else {

                            new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_QUESTION_BY_TAG + strtext);

                        }
                        textViewAnswer1.setBackground(null);
                        textViewAnswer1.setText("");
                        textViewAnswer2.setBackground(null);
                        textViewAnswer2.setText("");
                        textViewAnswer3.setBackground(null);
                        textViewAnswer3.setText("");
                        textViewAnswer4.setBackground(null);
                        textViewAnswer4.setText("");

                    }
                });


            }
        }, 2000);

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
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            final JSONObject resultObject;
            final JSONArray answersObject;
            final JSONArray objArray;
            answerList = new ArrayList<Answer>();
            if (strtext.equalsIgnoreCase("general")) {

                try {
                    Log.d("here", "inside Random Question");
                    JSONObject jsonObject = new JSONObject(s);
                    String question = jsonObject.getString("question");
                    boolean choice = jsonObject.getBoolean("multiple_choice");
                    objArray = jsonObject.getJSONArray("answers");
                    int answers_length = objArray.length();
                    for (int i = 0; i < answers_length; i++) {

                        JSONObject object = objArray.getJSONObject(i);
                        String answer_text = object.getString("answer_text");
                        Boolean valid = object.getBoolean("correct");
                        Log.d("Tagrr ", answer_text);
                        answerList.add(new Answer(answer_text, valid));
                        //Log.d("Tag inside random question",answer_text + " ");
                    }

                    question_view.setText(question);
                    if (answers_length > 0)
                        textViewAnswer1.setText(answerList.get(0).getResponse());
                    if (answers_length > 1)
                        textViewAnswer2.setText(answerList.get(1).getResponse());
                    if (answers_length > 2)
                        textViewAnswer3.setText(answerList.get(2).getResponse());
                    if (answers_length > 3)
                        textViewAnswer4.setText(answerList.get(3).getResponse());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.INVISIBLE);
                Log.d("Tagggg", "response " + s);
                super.onPostExecute(s);
            } else {

                try {
                    Log.d("here", "inside Question by tag");
                    JSONObject jsonObject = new JSONObject(s);
                    resultObject = jsonObject.getJSONObject("result");
                    answersObject = resultObject.getJSONArray("answers");
                    String question = resultObject.getString("question");
                    int answers_length = answersObject.length();
                    for (int i = 0; i < answers_length; i++) {

                        JSONObject answer = answersObject.getJSONObject(i);
                        String answer_text = answer.getString("answer_text");
                        Boolean valid = answer.getBoolean("correct");
                        answerList.add(new Answer(answer_text, valid));
                        Log.d("Tag", "response " + answer_text + " ");

                    }

                    question_view.setText(question);
                    if (answers_length > 0)
                        textViewAnswer1.setText(answerList.get(0).getResponse());
                    if (answers_length > 1)
                        textViewAnswer2.setText(answerList.get(1).getResponse());
                    if (answers_length > 2)
                        textViewAnswer3.setText(answerList.get(2).getResponse());
                    if (answers_length > 3)
                        textViewAnswer4.setText(answerList.get(3).getResponse());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.INVISIBLE);
                Log.d("Tag", "response " + s);
                super.onPostExecute(s);

            }

        }
    }

}
