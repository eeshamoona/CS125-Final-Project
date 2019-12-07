package com.example.cs_125_project_final;

import android.os.Parcel;
import android.os.Parcelable;

public class GoalClass implements Parcelable {
    private String title;
    private String[] tasks = new String[0];

    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeStringArray(tasks);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<GoalClass> CREATOR = new Parcelable.Creator<GoalClass>() {
        public GoalClass createFromParcel(Parcel in) {
            return new GoalClass(in);
        }

        public GoalClass[] newArray(int size) {
            return new GoalClass[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private GoalClass(Parcel in) {
        title = in.readString();
    }

    public GoalClass(){

    }

    public String getTitle() {
        return title;
    }

    public String[] getTasks() {
        return tasks;
    }

    public void setTitle(String s) {
        title = s;
    }

    public void addTask(String arg) {
        int length = tasks.length + 1;
        String[] temp = new String[length];
        for (int i = 0; i < length - 1; i++) {
            temp[i] = tasks[i];
        }
        temp[length - 1] = arg;
        tasks = temp;
    }

    public void removeTask(String arg) {
        int length = tasks.length - 1;
        String[] temp = new String[length];
        int counter = 0;
        for (int i = 0; i < tasks.length; i++) {
            if(!tasks[i].equals(arg)){
                temp[counter] = tasks[i];
                counter++;
            }
        }
        tasks = temp;
    }
}
