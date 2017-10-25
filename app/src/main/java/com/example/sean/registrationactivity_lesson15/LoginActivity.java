package com.example.sean.registrationactivity_lesson15;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sean.registrationactivity_lesson15.App.AppConfig;
import com.example.sean.registrationactivity_lesson15.App.AppController;
import com.example.sean.registrationactivity_lesson15.Helper.SQLiteHandler;
import com.example.sean.registrationactivity_lesson15.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    Button login,signup;
    EditText username,password;
    SQLiteHandler sqLiteHandler;
    SessionManager sessionManager;

    HashMap<String,String> user;
    ProgressDialog progressDialog;
    String getDetailsUrl;
    TextView emailView,passView,forgotPassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        username = (EditText) findViewById(R.id.enterUsername);
        password = (EditText) findViewById(R.id.enterPassword);
        forgotPassView = (TextView) findViewById(R.id.forgotPass);
        emailView = (TextView) findViewById(R.id.mailView);
        passView = (TextView) findViewById(R.id.passView);
        getDetailsUrl = AppConfig.URL_GET_DETAILS;

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        sqLiteHandler = new SQLiteHandler(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        /*if(sessionManager.IsLoggedIn()){
            Intent intent = new Intent(LoginActivity.this,NavigationDrawer.class);
            startActivity(intent);
            finish();
        }*/

        forgotPassView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username1 = username.getText().toString().trim()
                        ,passoword1 = password.getText().toString().trim();
                if(username1.isEmpty()||passoword1.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username or password are missing", Toast.LENGTH_SHORT).show();
                }else {
                    //Intent intent = new Intent(LoginActivity.this,
                      //      NavigationDrawer.class);
                    //intent.putExtra("tokenUrl",getDetailsUrl);
                    //startActivity(intent);
                    //finish();
                    checkLogin(username1, passoword1);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



      ///////TEST
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        progressDialog.setMessage("Logging in ...");
        showDialog();
        Log.i("TEST", "hello");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    String token = jObj.getString("Token");

                    // Check for error node in json
                    if (token!="null") {
                        // user successfully logged in
                        // Create login session
                        sessionManager.SetLogin(true);

                        // Now store the user in SQLite
                        String newGetDetailsURL = getDetailsUrl+"/"+token;
                        sqLiteHandler.addUser(email,password,token);


                        String tag_details_req = "req_details";

                        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                                newGetDetailsURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response1) {
                                Log.d(TAG, "Getting Details Response: " + response1.toString());

                                try {
                                    JSONObject jasun = new JSONObject(response1);
                                    JSONArray Cards = jasun.getJSONArray("Cards");
                                    for(int i=0;i<Cards.length();i++){
                                        JSONObject card = Cards.getJSONObject(i);
                                        String cardId = card.getString("CardId")
                                                ,balance = card.getString("Balance")
                                                ,cardMAsk = card.getString("CardMask")
                                                ,bankAcc = card.getString("BankAccount")
                                                ,currency = card.getString("Currency")
                                                ,cardType = card.getString("CardType")
                                                ,status = card.getString("Status")
                                                ,expM = card.getString("ExpirationMonth")
                                                ,expY = card.getString("ExpirationYear")
                                                ,cardAccId = card.getString("WalletProviderCardAccountId");
                                        sqLiteHandler.addCards(cardId,balance,cardMAsk,bankAcc,currency,
                                                cardType,status,expM,expY,cardAccId);
                                    }
                                    //Toast.makeText(LoginActivity.this, ids.toString(), Toast.LENGTH_SHORT).show();

                                    String status = jasun.getString("Status")
                                            ,firstName = jasun.getString("FirstName")
                                            ,lastName =jasun.getString("LastName")
                                            ,email = jasun.getString("Email")
                                            ,country = jasun.getString("Country")
                                            ,countryAlpha2 = jasun.getString("CountryAlpha2")
                                            ,city = jasun.getString("City")
                                            ,zip = jasun.getString("Zip")
                                            ,address = jasun.getString("Address")
                                            ,userAccStatus  = jasun.getString("UserAccStatus")
                                            ,skypeName = jasun.getString("SkypeName")
                                            ,countryName = jasun.getString("CountryName")
                                            ,phoneCode = jasun.getString("Phonecode")
                                            ,phone = jasun.getString("Phone")
                                            ,deviceId = jasun.getString("DeviceIdentificator");

                                    sqLiteHandler.addDetails(status,firstName,lastName,email,country,countryAlpha2
                                    ,city,zip,address,userAccStatus,skypeName,phoneCode,phone,countryName,deviceId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, "Getting Details Error: " + error.getMessage());
                                Toast.makeText(getApplicationContext(),
                                        error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                        try {
                            AppController.getInstance().addToRequestQueue(stringRequest);
                        }catch(Exception ex) {
                            ex.printStackTrace();
                        }
                        //Toast.makeText(LoginActivity.this, ids.toString(), Toast.LENGTH_SHORT).show();
                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                NavigationDrawer.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        Toast.makeText(getApplicationContext(),
                                "User does not exist", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", email);
                params.put("password", password);
                params.put("DeviceIdentificator","123412348");

                return params;
            }

        };

        try {
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        }catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    public void language(View view){
        switch(view.getId()){
            case R.id.ukranianLangBtn:
                username.setHint(R.string.enter_email_ukr);
                password.setHint(R.string.enter_password_ukr);
                forgotPassView.setText(R.string.forgot_password_ukr);
                passView.setText(R.string.password_view_ukr);
                login.setText(R.string.login_button_text_ukr);
                signup.setText(R.string.join_button_text_ukr);
                break;
            case R.id.russianLangBtn:
                username.setHint(R.string.enter_email_ru);
                password.setHint(R.string.enter_password_ru);
                forgotPassView.setText(R.string.forgot_password_ru);
                passView.setText(R.string.password_view_ru);
                login.setText(R.string.login_button_text_ru);
                signup.setText(R.string.join_button_text_ru);
                break;
            case R.id.englishLangBtn:
                username.setHint(R.string.enter_email);
                password.setHint(R.string.enter_password);
                forgotPassView.setText(R.string.forgot_password);
                passView.setText(R.string.password_view);
                login.setText(R.string.login_button_text);
                signup.setText(R.string.join_button_text);
                break;
        }
    }
    private void showDialog(){
        if(!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog(){
        progressDialog.dismiss();
    }
}
