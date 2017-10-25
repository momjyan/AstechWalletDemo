package com.example.sean.registrationactivity_lesson15.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sean.registrationactivity_lesson15.Helper.SQLiteHandler;
import com.example.sean.registrationactivity_lesson15.R;

public class SettingsActivity extends Fragment {
    TextView editPD,editPass;
    String addres="",skyp="",phon="",pass="",confirmpass="";
    TextView address,phoneNum,skypeAcc,PasswordView,accStatusView,emailView;
    EditText editpass,confirmPass,oldpass;
    SQLiteHandler sqLiteHandler;
    EditText editAddress,editPhone,editSkype;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings,container,false);
        editPD = (TextView) view.findViewById(R.id.editPD);
        editPass = (TextView) view.findViewById(R.id.editPassV);
        address = (TextView) view.findViewById(R.id.addressView);
        phoneNum = (TextView) view.findViewById(R.id.phoneView);
        skypeAcc = (TextView) view.findViewById(R.id.skypeView);
        PasswordView = (TextView) view.findViewById(R.id.PasswordView);
        accStatusView = (TextView) view.findViewById(R.id.accStatusView);
        emailView = (TextView) view.findViewById(R.id.emailView);

        sqLiteHandler = new SQLiteHandler(getActivity());

        address.setText(sqLiteHandler.getUserDetails().get("Address"));
        phoneNum.setText(sqLiteHandler.getUserDetails().get("Phone"));
        skypeAcc.setText(sqLiteHandler.getUserDetails().get("SkypeName"));
        accStatusView.setText(sqLiteHandler.getUserDetails().get("UserAccStatus"));
        PasswordView.setText(sqLiteHandler.getUser().get("password"));
        emailView.setText(sqLiteHandler.getUser().get("login"));


        editPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPD();
            }
        });
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPass();
            }
        });

        return view;
    }
    public void editPD(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_personal_info_edit, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Edit personal information");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                editAddress = (EditText) dialogView.findViewById(R.id.editAddress);
                editPhone = (EditText) dialogView.findViewById(R.id.editPhone);
                editSkype = (EditText) dialogView.findViewById(R.id.editSkype);

                addres = editAddress.getText().toString();
                if (!addres.isEmpty())
                    address.setText(addres);
                skyp = editSkype.getText().toString();
                if (!skyp.isEmpty())
                    skypeAcc.setText(skyp);
                phon = editPhone.getText().toString();
                if (!phon.isEmpty())
                    phoneNum.setText(phon);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void editPass(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_password, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Edit password");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                editpass = (EditText) dialogView.findViewById(R.id.insertNpass);
                oldpass = (EditText) dialogView.findViewById(R.id.insertCpass);
                confirmPass = (EditText) dialogView.findViewById(R.id.confirmpass);

                pass = editpass.getText().toString();
                confirmpass = confirmPass.getText().toString();
                if (pass.isEmpty()||confirmpass.isEmpty())
                    Toast.makeText(getActivity(), "Password hasn't changed, Please don't leave blank fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(confirmpass)&&oldpass.getText().toString().equals(sqLiteHandler.getUser().get("password"))) {
                        PasswordView.setText(pass);
                    } else {
                        Toast.makeText(getActivity(), "One of the passwords is incorrect!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
}
