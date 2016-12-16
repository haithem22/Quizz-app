package web.afor.innovation.quizapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.R;


public class AddNewQuestionFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public AddNewQuestionFragment() {
        // Required empty public constructor
    }

    public JSONObject BuildJsonObject() {

        //JSONObject jsonObject = new JSONObject();
        JSONObject params = new JSONObject();
        try {
            params.put("email", "ss@ss.com");
            params.put("token", "8e71c639fe038842681b0f62219007a9fb1888cc");

            JSONArray answers = new JSONArray();

            JSONObject answer1 = new JSONObject();

            answer1.put("answer_text", "16");
            answer1.put("correct", true);

            answers.put(answer1);

            JSONObject answer2 = new JSONObject();

            answer2.put("answer_text", "10");
            answer2.put("correct", false);

            answers.put(answer2);


            params.put("answers",answers);


            params.put("question", "what is 8 plus 8");
            params.put("multiple_choice", false);

            JSONArray tagsArray = new JSONArray();

            tagsArray.put("math");
            tagsArray.put("space");

            params.put("tags",tagsArray);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        JSONObject testObject = BuildJsonObject();
        HttpPost httppost = new HttpPost(Constants.BASE_URL + "api/question");
        try {

            httppost.setEntity(new StringEntity(testObject.toString(), "UTF-8"));
            //response = httpClient.execute(httpPost);
            //HttpClient
            Log.d("Add",testObject.toString());
            Log.d("AddQuestion", "in AddQuestionFragment");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_add_new_question, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
