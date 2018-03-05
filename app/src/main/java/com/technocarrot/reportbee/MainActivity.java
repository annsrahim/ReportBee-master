package com.technocarrot.reportbee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.technocarrot.student.StudentHomeActivity;
import com.technocarrot.loginRegister.LoginActivity;
import com.technocarrot.teacher.TeacherHomeActivity;
import com.technocarrot.utils.Constants;
import com.technocarrot.utils.SharedPreferenceUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        int isLogged = SharedPreferenceUtils.getIntDataInShare(this,Constants.isLogged);

        if(isLogged == 0)
        {

            Intent intent  = new Intent(this, LoginActivity.class);
            startActivity(intent);

        }
        else
        {
            Intent intent  = new Intent(this, TeacherHomeActivity.class);
            startActivity(intent);
        }

    }
}
