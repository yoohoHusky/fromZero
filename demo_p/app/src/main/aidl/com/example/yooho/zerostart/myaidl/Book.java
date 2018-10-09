package com.example.yooho.zerostart.myaidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by haoou on 2018/8/30.
 */

public class Book implements Parcelable{
    private String name;
    private int price;

    public Book() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    protected Book(Parcel in) {
        name = in.readString();
        price = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(name);
        parcel.writeInt(price);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        price = dest.readInt();
    }

    @Override
    public String toString() {
        return "{name:" + name + ",price:" + price + "}";
    }
}
