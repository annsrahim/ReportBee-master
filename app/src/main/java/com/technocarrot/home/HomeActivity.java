package com.technocarrot.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.technocarrot.loginRegister.LoginActivity;
import com.technocarrot.reportbee.R;
import com.technocarrot.utils.Constants;
import com.technocarrot.utils.SharedPreferenceUtils;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferenceUtils.setIntDataInShare(this, Constants.isLogged,1);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("https://www.reportbee.com/who_we_are");
    }

    public void doSignOut(View view) {
        SharedPreferenceUtils.setIntDataInShare(this, Constants.isLogged,0);
        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

    }
}
