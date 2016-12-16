package web.afor.innovation.quizapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import web.afor.innovation.quizapp.R;
import web.afor.innovation.quizapp.webservices.UserController;

public class Activity_login extends AppCompatActivity {

    int mode_connect = 0;

    Button sign_in;
    TextView changeMode_view;
    EditText email;
    ProgressBar progressBar;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_login);

        sign_in = (Button) findViewById(R.id.connect);
        changeMode_view = (TextView) findViewById(R.id.changeMode_view);
        email = (EditText) findViewById(R.id.ed_email);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        password = (EditText) findViewById(R.id.ed_password);


    }

    public void login_method(View v) {
        //Toast.makeText(this, "sign in", Toast.LENGTH_LONG);
        if (mode_connect == 1) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        } else {
            /*Intent intent = new Intent(this, QuizActivity.class);
            //Log.i("sentemail", email.getText().toString());
            intent.putExtra("email", email.getText().toString());
            startActivity(intent);*/
            UserController userController = new UserController(this, progressBar);
            userController.seConnecter(email.getText().toString(), password.getText().toString());
        }
    }

    public void Alternate_sign_in_up(View v) {

        if (mode_connect == 0) {
            sign_in.setText("sign up");
            mode_connect = 1;
        } else {
            sign_in.setText("sign in");
            mode_connect = 0;
        }

    }
    /*public void inscription_method(){
        Toast.makeText(this, "sign up", Toast.LENGTH_LONG);
    }*/

}
