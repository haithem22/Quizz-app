package web.afor.innovation.quizapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import web.afor.innovation.quizapp.Activities.QuizActivity;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.Models.Answer;
import web.afor.innovation.quizapp.R;


public class QuizFragment extends Fragment {

    private TextView question_view;
    private TextView player1_view;
    private TextView player2_view;
    private TextView player3_view;
    private TextView player4_view;
    private TextView score_view;
    private RelativeLayout progress_view;
    private TextView username_view;

    private ArrayList<Answer> answer;

    private int score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_quiz, container, false);

        new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);
        question_view = (TextView) view.findViewById(R.id.question_view);
        player1_view = (TextView) view.findViewById(R.id.player1_view);
        player2_view = (TextView) view.findViewById(R.id.player2_view);
        player3_view = (TextView) view.findViewById(R.id.player3_view);
        player4_view = (TextView) view.findViewById(R.id.player4_view);
        score_view = (TextView) view.findViewById(R.id.score_view);
        progress_view = (RelativeLayout) view.findViewById(R.id.progress);
        username_view = (TextView) view.findViewById(R.id.username_view);

        score = Preferences.loadInt(getContext(), "score", 0);
        score_view.setText(String.valueOf(score));
        username_view.setText(Preferences.loadString(getContext(), "name_user", ""));

        player1_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.get(0).isValidAnswer()) {
                    player1_view.setBackground(getResources().getDrawable(R.drawable.back));
                    score += Constants.INC_VALUE;
                    save();
                    newQuestion();
                    //Toast.makeText(QuizActivity.this, "Correct!!! =)", Toast.LENGTH_LONG).show();
                } else {
                    score += Constants.DEC_VALUE;
                    save();
                    player1_view.setBackground(getResources().getDrawable(R.drawable.falseanswer));
                    //Toast.makeText(QuizActivity.this, "False!!! =)", Toast.LENGTH_LONG).show();
                }
            }
        });

        player2_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.get(1).isValidAnswer()) {
                    player2_view.setBackground(getResources().getDrawable(R.drawable.back));
                    newQuestion();
                    score += Constants.INC_VALUE;
                    save();
                    //Toast.makeText(QuizActivity.this, "Correct!!! =)", Toast.LENGTH_LONG).show();
                } else {
                    score += Constants.DEC_VALUE;
                    save();
                    player2_view.setBackground(getResources().getDrawable(R.drawable.falseanswer));
                    //Toast.makeText(QuizActivity.this, "False!!! =)", Toast.LENGTH_LONG).show();
                }
            }
        });

        player3_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.get(2).isValidAnswer()) {

                    player3_view.setBackground(getResources().getDrawable(R.drawable.back));
                    newQuestion();
                    score += Constants.INC_VALUE;
                    save();
                    //Toast.makeText(QuizActivity.this, "Correct!!! =)", Toast.LENGTH_LONG).show();
                } else {
                    score += Constants.DEC_VALUE;
                    save();
                    player3_view.setBackground(getResources().getDrawable(R.drawable.falseanswer));
                    //Toast.makeText(QuizActivity.this, "False!!! =)", Toast.LENGTH_LONG).show();
                }
            }
        });

        player4_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.get(3).isValidAnswer()) {
                    player4_view.setBackground(getResources().getDrawable(R.drawable.back));
                    newQuestion();
                    score += Constants.INC_VALUE;
                    save();
                    //Toast.makeText(QuizActivity.this, "Correct!!! =)", Toast.LENGTH_LONG).show();
                } else {
                    score += Constants.DEC_VALUE;
                    save();
                    player4_view.setBackground(getResources().getDrawable(R.drawable.falseanswer));
                    //Toast.makeText(QuizActivity.this, "False!!! =)", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

    public void newQuestion() {
        android.os.Handler handler = new android.os.Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //RESET_ALL_VIEWS
                Reset_All_View();

                new DownloadQuestion().execute(Constants.BASE_URL + Constants.URL_RANDOM);
                progress_view.setVisibility(View.VISIBLE);
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
        Preferences.saveInt(getContext(), "score", score);
        /*Context context = getContext();
        SharedPreferences sharedPref = context.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.d("sc",score+"");
        editor.putInt("score", score);
        editor.commit();*/
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


    // TODO: Rename method, update argument and hook method into UI event


}
