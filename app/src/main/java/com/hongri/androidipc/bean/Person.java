package com.hongri.androidipc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author zhongyao
 * @date 2018/6/1
 */

public class Person implements Parcelable {

    private int weight;
    private String school;

    public Person() {

    }

    public Person(Parcel in) {
        weight = in.readInt();
        school = in.readString();
    }

    /**
     * 反序列化
     */
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化
     * @param out
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(weight);
        out.writeString(school);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
