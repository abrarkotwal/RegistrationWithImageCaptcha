package com.abrarkotwal.socialapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.abrarkotwal.socialapp.R;
import com.abrarkotwal.socialapp.utils.RoomDB.DatabaseClient;
import com.abrarkotwal.socialapp.utils.RoomDB.User;
import com.abrarkotwal.socialapp.utils.captcha.TextCaptcha;
import com.abrarkotwal.socialapp.utils.LoginSessionManager;
import com.abrarkotwal.socialapp.utils.UiUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterationActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etMobile)
    EditText etMobile;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etCaptchaText)
    EditText etCaptchaText;

    @BindView(R.id.btnRefreshCaptha)
    ImageButton btnRefreshCaptcha;

    @BindView(R.id.captchaImage)
    ImageView captchaImage;

    @BindView(R.id.btnRegister)
    Button btnRegister;

    int numberOfCaptchaFalse = 0;
    String name,email,password,mobile;
    private TextCaptcha textCaptcha;
    LoginSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this,this);

        sessionManager = new LoginSessionManager(this);

        setCaptcha();

        btnRefreshCaptcha.setOnClickListener(v -> {
            setCaptcha();
        });

        btnRegister.setOnClickListener(v -> checkValidation());
    }

    private void setCaptcha() {
        textCaptcha = new TextCaptcha(600, 150, 4, TextCaptcha.TextOptions.LETTERS_ONLY);
        captchaImage.setImageBitmap(textCaptcha.getImage());
    }

    private void checkValidation() {

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        mobile = etMobile.getText().toString();

        Pattern p = Pattern.compile(UiUtils.regEx);
        Matcher m = p.matcher(email);


        if (!textCaptcha.checkAnswer(etCaptchaText.getText().toString().trim())) {
            etCaptchaText.setError("Captcha is not match");
            numberOfCaptchaFalse++;
            return;
        }

        if (name.isEmpty()) {
            etName.setError("Name required");
            etName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email required");
            etEmail.requestFocus();
            return;
        }
        else if (!m.find()){
            etEmail.setError("Invalid email");
            etEmail.requestFocus();
            return;
        }


        if (mobile.isEmpty()) {
            etMobile.setError("Mobile No required");
            etMobile.requestFocus();
            return;
        }
        else if (mobile.length() < 10){
            etMobile.setError("Enter valid Number");
            etMobile.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        @SuppressLint("StaticFieldLeak")
        class Registration extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                List<User> userList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .userDao()
                        .checkLogin(email,password);

                if (userList.size() == 0) {
                    User user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setMobile(mobile);
                    user.setPassword(password);
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .userDao()
                            .insert(user);

                    return "Success";
                }
                else
                    return "Failure";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Success")) {
                    sessionManager.createLoginSession(email);
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Already Exist",Toast.LENGTH_SHORT).show();
                }
            }
        }

        Registration st = new Registration();
        st.execute();
    }
}
