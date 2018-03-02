package com.technocarrot.loginRegister;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.technocarrot.Config;
import com.technocarrot.beans.TokenBean;
import com.technocarrot.home.HomeActivity;
import com.technocarrot.reportbee.R;
import com.technocarrot.service.APIClient;
import com.technocarrot.service.APIInterface;
import com.technocarrot.utils.Constants;
import com.technocarrot.utils.LoadingDialog;
import com.technocarrot.utils.SharedPreferenceUtils;
import com.technocarrot.utils.Utilities;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    APIInterface apiInterface;
    ProgressDialog progressDialog;
    EditText edEmail,edName,edPassword,edConfirmPassword;
    final String[] mRolesArray = {"teacher","student"};
    final String[] mRolesNameArray = {"Teacher","Student"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        apiInterface = APIClient.getClient(this).create(APIInterface.class);
        getAuthorizationToken();
    }

    private void getAuthorizationToken() {

    }

    private void initUI() {
        edEmail = (EditText)findViewById(R.id.ed_email);
        edName = (EditText)findViewById(R.id.ed_first_name);
        edPassword = (EditText)findViewById(R.id.ed_password);
        edConfirmPassword = (EditText)findViewById(R.id.ed_password2);
    }

    public void doRegister(View view) {

        if(!isValidDatas())
            return;
        getRoleFromUser();

    }

    private void getRoleFromUser() {

        new AlertDialog.Builder(this)
                .setTitle("Iam a")
                .setSingleChoiceItems(mRolesNameArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Utilities.showToast(RegisterActivity.this,"Selected Role :"+mRolesArray[i]);
                        dialogInterface.dismiss();
                        updateUserInfo(mRolesArray[i]);

                    }
                })
                .show();
    }

    private void updateUserInfo(String role) {
        progressDialog  = LoadingDialog.show(this,"Creating new user..");
        if(Utilities.isNetworkConnected(this))
        {
            Call<TokenBean> loginCall = apiInterface.doRegisterUser(edName.getText().toString(),
                    edEmail.getText().toString(),
                    edPassword.getText().toString(),
                    edConfirmPassword.getText().toString(),
                    role
                    );
            loginCall.enqueue(new Callback<TokenBean>() {
                @Override
                public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null)
                    {
                        SharedPreferenceUtils.setStringDataInShare(RegisterActivity.this, Constants.TOKEN_TYPE,response.body().getTokenType());
                        SharedPreferenceUtils.setStringDataInShare(RegisterActivity.this, Constants.ACCESS_TOKEN,response.body().getAccessToken());
                        SharedPreferenceUtils.setStringDataInShare(RegisterActivity.this, Constants.EXPIRES_IN,response.body().getExpiresIn());
                        SharedPreferenceUtils.setStringDataInShare(RegisterActivity.this, Constants.REFRESH_TOKEN,response.body().getRefreshToken());
                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
//                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
//                        startActivity(intent);
                        Utilities.showToast(RegisterActivity.this,"Email already exists Please try with different email");
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

    private boolean isValidDatas() {
        String _email = edEmail.getText().toString();
        String _name = edName.getText().toString();
        String _password = edPassword.getText().toString();
        String _confirmPassword = edConfirmPassword.getText().toString();

        if(_email.isEmpty() || _name.isEmpty() || _password.isEmpty() || _confirmPassword.isEmpty())
        {
            Utilities.showToast(this,"Please Enter All details");
            return false;
        }
        else
        {
            if(!Utilities.isValidEmail(_email))
            {
                Utilities.showToast(this,"Please Enter Valid Email");
                return false;
            }
            else if(!_password.equalsIgnoreCase(_confirmPassword))
            {
                Utilities.showToast(this,"Password Does not match");
                return false;
            }
            else
            {
                return true;
            }
        }


    }
}
