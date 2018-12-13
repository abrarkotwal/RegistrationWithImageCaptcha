package com.abrarkotwal.socialapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.utils.RoomDB.DatabaseClient;
import com.abrarkotwal.socialapp.utils.RoomDB.User;
import com.abrarkotwal.socialapp.utils.captcha.TextCaptcha;
import com.abrarkotwal.socialapp.ui.home.HomeActivity;
import com.abrarkotwal.socialapp.utils.LoginSessionManager;
import com.abrarkotwal.socialapp.utils.UiUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etUsername)
    EditText etUsername;
    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnGoRegister)
    TextView btnGoRegister;

    String email,password;
    private TextCaptcha textCaptcha;
    private LoginSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this,this);

        sessionManager = new LoginSessionManager(this);

        btnGoRegister.setPaintFlags(btnGoRegister.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        btnGoRegister.setOnClickListener(v -> {
            Intent intent =new Intent(LoginActivity.this,RegisterationActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> checkLogin());
    }

    private void checkLogin() {
        email = etUsername.getText().toString();
        password = etPass.getText().toString();

        Pattern p = Pattern.compile(UiUtils.regEx);
        Matcher m = p.matcher(email);

        if (email.isEmpty()) {
            etUsername.setError("Email required");
            etUsername.requestFocus();
            return;
        }
        else if (!m.find()){
            etUsername.setError("Invalid email");
            etUsername.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPass.setError("Password required");
            etPass.requestFocus();
            return;
        }

        @SuppressLint("StaticFieldLeak")
        class CheckLogin extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                return DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .userDao()
                        .checkLogin(email,password);
            }

            @Override
            protected void onPostExecute(List<User> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size() == 0){
                    Toast.makeText(getApplicationContext(),"Login Failure. Check UserName and Password",Toast.LENGTH_SHORT).show();
                }else{
                    sessionManager.createLoginSession(email);
                    Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

        CheckLogin gt = new CheckLogin();
        gt.execute();

    }
}
