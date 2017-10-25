package com.example.sean.registrationactivity_lesson15.activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sean.registrationactivity_lesson15.Helper.PrepaidCard;
import com.example.sean.registrationactivity_lesson15.Helper.SQLiteHandler;
import com.example.sean.registrationactivity_lesson15.R;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends Fragment {
    TextView cardTypeView,balanceView,bankAccView,cardNumView,expDateView,cardStatus;
    List<String> cardIds = new ArrayList<String>();
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_account,container,false);
        cardTypeView = (TextView) view.findViewById(R.id.cardTypeView);
        balanceView = (TextView) view.findViewById(R.id.balanceView);
        bankAccView = (TextView) view.findViewById(R.id.bankAccountView);
        cardNumView = (TextView) view.findViewById(R.id.cardNumberView);
        expDateView = (TextView) view.findViewById(R.id.expirationDateView);
        cardStatus = (TextView) view.findViewById(R.id.cardStatusView);
        final SQLiteHandler sqlite = new SQLiteHandler(getActivity());
        spinner = (Spinner) view.findViewById(R.id.cardIdSpinner);

        cardIds = sqlite.getCardIds();
        PrepaidCard[] prepaidCards = new PrepaidCard[cardIds.size()];

         for (int i=0;i<prepaidCards.length;i++){
             String Mask = sqlite.getCardInfo(cardIds.get(i)).get("CardMask");
             Mask.toCharArray();
             String mask="";
             for (int j=Mask.length()-4;j<Mask.length();j++){
                 mask += Mask.charAt(j);
             }

             String Currency = sqlite.getCardInfo(cardIds.get(i)).get("Currency");
             prepaidCards[i] = new PrepaidCard(cardIds.get(i),Currency,mask);
         }


        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item, prepaidCards);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PrepaidCard pc = (PrepaidCard) spinner.getSelectedItem();

                String CardId = String.valueOf(pc.getCardId());
                bankAccView.setText(sqlite.getCardInfo(CardId).get("BankAccount"));
                cardNumView.setText(sqlite.getCardInfo(CardId).get("CardMask"));
                expDateView.setText(sqlite.getCardInfo(CardId).get("ExpirationMonth")+"/"+sqlite.getCardInfo(CardId).get("ExpirationYear"));
                cardStatus.setText(sqlite.getCardInfo(CardId).get("Status"));
                cardTypeView.setText(sqlite.getCardInfo(CardId).get("CardType"));
                balanceView.setText(sqlite.getCardInfo(CardId).get("Balance"));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        return view;
    }
}
