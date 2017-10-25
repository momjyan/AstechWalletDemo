package com.example.sean.registrationactivity_lesson15;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPasswordActivity extends Activity {
    Button backToLogin,restore;
    EditText enterMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        backToLogin  = (Button) findViewById(R.id.backToLoginBtn);
        restore = (Button) findViewById(R.id.restorePassBtn);
        enterMail = (EditText) findViewById(R.id.enterEmail);

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{enterMail.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "Hi");
                startActivity(Intent.createChooser(i, "Send mail..."));
            }
        });*/
    }

    public void language(View view){
        switch(view.getId()){
            case R.id.ukranianLangBtn:
                backToLogin.setText(R.string.back_to_login_ukr);
                restore.setText(R.string.restore_ukr);
                enterMail.setHint(R.string.enter_email_ukr);
                break;
            case R.id.russianLangBtn:
                backToLogin.setText(R.string.back_to_login_ru);
                restore.setText(R.string.restore_ru);
                enterMail.setHint(R.string.enter_email_ru);
                break;
            case R.id.englishLangBtn:
                backToLogin.setText(R.string.back_to_login);
                restore.setText(R.string.restore);
                enterMail.setHint(R.string.enter_email);
                break;
        }
    }
}
