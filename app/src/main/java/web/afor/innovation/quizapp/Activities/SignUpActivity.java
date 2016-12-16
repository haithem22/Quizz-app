package web.afor.innovation.quizapp.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import web.afor.innovation.quizapp.R;
import web.afor.innovation.quizapp.webservices.UserController;

public class SignUpActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    EditText password_repeated;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = (EditText) findViewById(R.id.username_inscription);
        email = (EditText) findViewById(R.id.ed_email_inscription);
        password = (EditText) findViewById(R.id.password_inscription);
        password_repeated = (EditText) findViewById(R.id.password_inscription_repeat);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    //edPass.getBackground().mutate().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
    public void addAccount(View v) {
        if (password.getText().toString().equals(password_repeated.getText().toString())) {
            UserController userController = new UserController(this, progressBar);
            userController.inscription(email.getText().toString(), password.getText().toString(), username.getText().toString());
        } else {
            password.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
            password_repeated.getBackground().mutate().setColorFilter(getResources().getColor(R.color.Red), PorterDuff.Mode.SRC_ATOP);
        }


    }
}
