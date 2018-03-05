package com.technocarrot.loginRegister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.technocarrot.Config;
import com.technocarrot.student.StudentHomeActivity;
import com.technocarrot.teacher.TeacherHomeActivity;
import com.technocarrot.utils.Constants;
import com.technocarrot.utils.LoadingDialog;
import com.technocarrot.utils.SharedPreferenceUtils;
import com.technocarrot.utils.Utilities;
import com.technocarrot.beans.TokenBean;
import com.technocarrot.reportbee.R;
import com.technocarrot.service.APIClient;
import com.technocarrot.service.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    EditText edEmail,edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utilities.disableKeyboardPopup(this);
        initUI();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
    }

    private void initUI() {
        edEmail = (EditText)findViewById(R.id.ed_email);
        edPassword = (EditText)findViewById(R.id.ed_password);
    }

    public void doSignIn(View view) {

        if(!isValidDatas())
            return;

        progressDialog  = LoadingDialog.show(this,"Authenticating..");
        if(Utilities.isNetworkConnected(this))
        {
            Call<TokenBean> loginCall = apiInterface.doLoginCheck("password",
                                                            Config.clientId,
                                                            Config.clientSecret,
                                                            edEmail.getText().toString(),
                                                            edPassword.getText().toString(),
                                                                "*");
            loginCall.enqueue(new Callback<TokenBean>() {
                @Override
                public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null)
                    {
                        SharedPreferenceUtils.setStringDataInShare(LoginActivity.this, Constants.TOKEN_TYPE,response.body().getTokenType());
                        SharedPreferenceUtils.setStringDataInShare(LoginActivity.this, Constants.ACCESS_TOKEN,response.body().getAccessToken());
                        SharedPreferenceUtils.setStringDataInShare(LoginActivity.this, Constants.EXPIRES_IN,response.body().getExpiresIn());
                        SharedPreferenceUtils.setStringDataInShare(LoginActivity.this, Constants.REFRESH_TOKEN,response.body().getRefreshToken());
                       SharedPreferenceUtils.setIntDataInShare(LoginActivity.this,Constants.isLogged,1);
                        Utilities.getUserInfo(LoginActivity.this);
//                        Intent intent = new Intent(LoginActivity.this, TeacherHomeActivity.class);
//                        startActivity(intent);
                    }
                    else
                    {
                        Utilities.showToast(LoginActivity.this,"Invalid Login Please Check Username or Password");
                    }

                }

                @Override
                public void onFailure(Call<TokenBean> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("ResponseTest","Failed");
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

    }

    private boolean isValidDatas() {
        String emailValue = edEmail.getText().toString();
        String passwordValue = edPassword.getText().toString();

        if(edEmail.getText().toString().isEmpty()||edPassword.getText().toString().isEmpty())
        {
            Utilities.showToast(this,"Enter username and password");
            return false;
        }
        else if(!Utilities.isValidEmail(emailValue))
        {
            Utilities.showToast(this,"Invalid Email");
            return false;
        }
        else
            return true;

    }


    public void goToNewUser(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
