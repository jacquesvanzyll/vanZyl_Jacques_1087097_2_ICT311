package com.bignerdranch.android.testing1;


import static android.R.attr.duration;

public class Setting {

    //private variables
    int _id;
    String name;
    String email;
    String gender;
    String comment;

    public Setting(){

    }

    public Setting(int id, String name, String email, String gender, String comment){
        this._id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.comment = comment;

    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}