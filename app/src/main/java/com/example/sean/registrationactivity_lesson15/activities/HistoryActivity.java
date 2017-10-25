package com.example.sean.registrationactivity_lesson15.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sean.registrationactivity_lesson15.R;

import java.util.Calendar;

public class HistoryActivity extends Fragment {

    TextView fromDate,toDate;
    Button clear,apply;
    Spinner spinner1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_history, container, false);
        fromDate = (TextView) view.findViewById(R.id.fromDateView);
        toDate = (TextView) view.findViewById(R.id.toDateView);
        clear = (Button) view.findViewById(R.id.clearTopUpBtn);
        apply = (Button) view.findViewById(R.id.applyTopUpBtn);
        spinner1 = (Spinner) view.findViewById(R.id.transactionSpinner);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate.setText("");
                toDate.setText("");
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), R.style.MyDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                                toDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);

                            }
                        }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Pick a date");
                mDatePicker.show();
            }
        });

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(), R.style.MyDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                                fromDate.setText(selectedday + "/" + selectedmonth + "/" + selectedyear);

                            }
                        }, mYear, mMonth, mDay);
                //mDatePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Pick a date");
                mDatePicker.show();
            }
        });

        return view;
    }


    public void openDialog() {
        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setContentView(R.layout.calendar_view);
        dialog.setTitle("Pick a date");
        dialog.show();
    }
}
