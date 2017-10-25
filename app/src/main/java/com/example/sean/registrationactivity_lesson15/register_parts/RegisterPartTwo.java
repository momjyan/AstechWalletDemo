package com.example.sean.registrationactivity_lesson15.register_parts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.sean.registrationactivity_lesson15.R;

/**
 * Created by Sean on 8/31/2017.
 */

public class RegisterPartTwo extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_part_two);


    }
    public void next(View view){

        Intent intent = new Intent(this,RegisterPartThree.class);
        startActivity(intent);
        finish();

    }
}
