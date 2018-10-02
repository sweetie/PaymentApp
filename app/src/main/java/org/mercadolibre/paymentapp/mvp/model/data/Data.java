package org.mercadolibre.paymentapp.mvp.model.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {

    private String amount;
    private String paymentMethod;
    private String namePaymentMethod;
    private String cardIssuer;
    private String nameCardIssuer;
    private String dues;

    public Data(){

    }

    protected Data(Parcel in) {
        amount = in.readString();
        paymentMethod = in.readString();
        namePaymentMethod = in.readString();
        cardIssuer = in.readString();
        nameCardIssuer = in.readString();
        dues = in.readString();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(amount);
        parcel.writeString(paymentMethod);
        parcel.writeString(namePaymentMethod);
        parcel.writeString(cardIssuer);
        parcel.writeString(nameCardIssuer);
        parcel.writeString(dues);
    }

    public String getDues() {
        return dues;
    }

    public void setDues(String dues) {
        this.dues = dues;
    }

    public String getNamePaymentMethod() {
        return namePaymentMethod;
    }

    public void setNamePaymentMethod(String namePaymentMethod) {
        this.namePaymentMethod = namePaymentMethod;
    }

    public String getNameCardIssuer() {
        return nameCardIssuer;
    }

    public void setNameCardIssuer(String nameCardIssuer) {
        this.nameCardIssuer = nameCardIssuer;
    }
}
