package web.afor.innovation.quizapp.webservices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import web.afor.innovation.quizapp.Activities.Activity_login;
import web.afor.innovation.quizapp.Activities.MainActivity;
import web.afor.innovation.quizapp.Activities.QuizActivity;
import web.afor.innovation.quizapp.AppController;
import web.afor.innovation.quizapp.Config.Constants;
import web.afor.innovation.quizapp.LocalData.Preferences;
import web.afor.innovation.quizapp.R;


/**
 * Created by adminlocal on 28/07/2016.
 */

public class UserController {



    /*
     Threads introduction :
    private void LaunchThread(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                /*runOnUniThread()

            }
        })
    }
    *****************************
    We can use
    Retrofit instead of volley

    jakeWharton on github
    *****************************
    */

    RequestQueue requestQueue;
    private ProgressBar progressBar;
    private Context context;

    public UserController(Context context, ProgressBar progressBar) {
        requestQueue = Volley.newRequestQueue(context);
        this.progressBar = progressBar;
        this.context = context;
    }


    //liste_event
    public void seConnecter(final String login, final String pwd) {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_connect";

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", login);
        params.put("pass", pwd);

        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.BASE_URL + Constants.URL_LOGIN, new JSONObject(params),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        Log.d("cccccccccc seConnecter", response.toString());

                        if (response.toString().contains("token")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();

                        }

                        /*ObjectMapper mapper = new ObjectMapper();
                        try {
                            User emp = mapper.readValue(response.toString(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //Log.d("cccccccccccc", "" + error.getMessage() + "," + error.toString());
                //pDialog.hide();
                Toast.makeText(context, "Verify your login or password!! !!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(myRequest, tag_json_obj);

    }

    public void inscription(final String email, final String pwd, final String username) {

        String tag_json_obj = "json_obj_inscrip";

        progressBar.setVisibility(View.VISIBLE);

        Map<String, String> params = new HashMap<String, String>();

        params.put("email", email);
        params.put("pass", pwd);
        params.put("nickname", username);

        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST, Constants.BASE_URL + Constants.URL_SIGNUP, new JSONObject(params),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.GONE);

                        Log.d("cccccccccc adduser", response.toString());

                        if (response.toString().contains("token")) {

                            Preferences.saveString(username, "name_user", context);
                            Intent intent = new Intent(context, MainActivity.class);
                            //intent.putExtra("email",email.toString());
                            //intent.putExtra("usename",username.toString());
                            //intent.putExtra("password",pwd.toString());
                            //Log.d("pass",password.getText().toString());
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }

                        /*ObjectMapper mapper = new ObjectMapper();
                        try {
                            User emp = mapper.readValue(response.toString(), User.class);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //Log.d("cccccccccccc", "" + error.getMessage() + "," + error.toString());
                //pDialog.hide();
                Toast.makeText(context, "problem!! !!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(myRequest, tag_json_obj);

    }
}
