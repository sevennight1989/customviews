package com.android.custview.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    public int age;
    public String name;
    public double money;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.age);
        dest.writeString(this.name);
        dest.writeDouble(this.money);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.age = in.readInt();
        this.name = in.readString();
        this.money = in.readDouble();
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
