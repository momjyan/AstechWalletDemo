package com.example.sean.registrationactivity_lesson15.Helper;

public class PrepaidCard{
    public int CardId;
    public String Currency;
    public String Mask; // 4 last digits of card

    public PrepaidCard(String CardId,String Currency,String Mask){
        this.CardId = Integer.parseInt(CardId);
        this.Currency = Currency;
        this.Mask = Mask;
    }

    public int getCardId() {
        return CardId;
    }

    public String toString(){
        return( Mask + " - " + Currency );
    }
}
