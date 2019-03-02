package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Model.SignupResponse;
import com.sourcey.materiallogindemo.Retrofit.RestClient;
import com.sourcey.materiallogindemo.Utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @BindView(R.id.fname)
    EditText _fnameText;
    @BindView(R.id.lname)
    EditText _lnameText;
    @BindView(R.id.email)
    EditText _emailText;
    @BindView(R.id.mobile)
    EditText _mobileText;
    @BindView(R.id.password)
    EditText _passwordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
       ScrollView sv = (ScrollView)findViewById(R.id.scroll);



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

       /* String fname = _fnameText.getText().toString();
        String lname= _lnameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();*/




        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String fname = _fnameText.getText().toString();
        String lname = _lnameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        if (fname.isEmpty() || fname.length() < 3) {
            _fnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _fnameText.setError(null);
        }

        if (lname.isEmpty()) {
            _lnameText.setError("Enter Valid Address");
            valid = false;
        } else {
            _lnameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }


        RequestBody fname1 = RequestBody.create(MediaType.parse("text/plain"), fname);
        RequestBody lname1 = RequestBody.create(MediaType.parse("text/plain"), lname);
        RequestBody email1 = RequestBody.create(MediaType.parse("text/plain"), email);
        RequestBody password1 = RequestBody.create(MediaType.parse("text/plain"),password);
        RequestBody mobile1 = RequestBody.create(MediaType.parse("text/plain"), mobile);
        Utils.showProgressDialog(this);

        RestClient.registerUser(fname1,lname1,email1,password1 , mobile1, new Callback<SignupResponse>() {


            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                Utils.dismissProgressDialog();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        Utils.displayToast(getApplicationContext(), "Successfuly registered");
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Failed Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Utils.dismissProgressDialog();
                Utils.displayToast(SignupActivity.this, "Unable to register, please try again later");

            }
        });
        return valid;
    }




}
