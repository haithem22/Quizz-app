package web.afor.innovation.quizapp.webservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import web.afor.innovation.quizapp.Activities.MainActivity;
import web.afor.innovation.quizapp.AppController;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;

/**
 * Created by haithem on 03/08/2016.
 */
public class AddQuestionController {

    RequestQueue requestQueue;
    private ProgressBar progressBar;
    private Context context;

    public AddQuestionController(Context context, ProgressBar progressBar) {
        requestQueue = Volley.newRequestQueue(context);
        this.progressBar = progressBar;
        this.context = context;
    }

    public void AddQuestion(final String email, final String token, final ArrayList answers, final String question, final String multiple_choice, ArrayList tags) {

        String tag_json_obj = "json_obj_inscrip";

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("token", token);
        params.put("answers", answers.toString());
        params.put("question",question);
        params.put("multiple_choice", multiple_choice);
        params.put("tags", tags.toString());

        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.BASE_URL + Constants.URL_ADD_QUESTION, new JSONObject(params),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        Log.d("addQuestion", response.toString());

                        if (response.toString().contains("answers")) {

                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(context, "problem!! !!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(myRequest, tag_json_obj);

    }
}
