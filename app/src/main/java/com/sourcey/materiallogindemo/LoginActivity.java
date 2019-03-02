package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Model.LoginResponse;
import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Retrofit.RestClient;
import com.sourcey.materiallogindemo.Utils.LocationPrefs;
import com.sourcey.materiallogindemo.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;


    @BindView(R.id.edit_email)
    EditText _emailText;

    @BindView(R.id.edit_Passwword)
    EditText _passwordText;

    @BindView(R.id.btn_login)
    Button _loginButton;

    @BindView(R.id.link_signup)
    TextView _signupLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

       ScrollView sv = (ScrollView)findViewById(R.id.scroll1);

        sv.setVerticalScrollBarEnabled(false);
        sv.setHorizontalScrollBarEnabled(false);



        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                login();

            }
        });



        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                      progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {


                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the LocationActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean check = true;

       String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            check = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            check = false;
        } else {
            _passwordText.setError(null);
        }



        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"),   email);
        RequestBody pwd = RequestBody.create(MediaType.parse("text/plain"), password);
        Utils.showProgressDialog(this);
        RestClient.LoginUser(email1,pwd, new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Utils.dismissProgressDialog();
                if (response != null && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if (Integer.parseInt(loginResponse.getStatus()) == 1) {
                        Utils.displayToast(LoginActivity.this, loginResponse.getMessage());
                        LocationPrefs.putString(getApplicationContext(),"loginId",loginResponse.getLoginDetails().get(0).getId());

                        Intent intent=new Intent(LoginActivity.this,LocationActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Status", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();

                }
            }





            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(LoginActivity.this, "Invalid login detail");

            }

        });
        return check;
    }


}
