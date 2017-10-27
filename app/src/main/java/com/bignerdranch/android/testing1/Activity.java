package com.bignerdranch.android.testing1;


public class Activity {

    //private variables
    int _id;
    String title;
    String date;
    String activityType;
    String place;
    int duration;
    String comment;
    byte[] image;


    public Activity(){

    }

    public Activity(int id, String title, String date, String activityType,String place,int duration,String comment, byte[] img){
        this._id = id;
        this.title = title;
        this.date = date;
        this.activityType = activityType;
        this.place = place;
        this.duration = duration;
        this.comment = comment;
        this.image = img;

    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting title
    public String getTitle(){
        return this.title;
    }

    // setting title
    public void setTitle(String title){
        this.title = title;
    }

    // getting date
    public String getDate(){
        return this.date;
    }

    // setting date
    public void setDate(String date){
        this.date = date;
    }

    // getting activityType
    public String getActivityType(){
        return this.activityType;
    }

    // setting activityType
    public void setActivityType(String fname){
        this.activityType = activityType;
    }

    // getting place
    public String getPlace(){
        return this.place;
    }

    // setting place
    public void setPlace(String place){
        this.place = place;
    }

    // getting duration
    public Integer getDuration(){
        return this.duration;
    }

    // setting duration
    public void setDuration(Integer duration){
        this.duration = duration;
    }

    // getting comment
    public String getComment(){
        return this.comment;
    }

    // setting comment
    public void setComment(String duration){
        this.comment = comment;
    }

    //getting pic
    public byte[] getImage(){
        return this.image;
    }

    //setting pic
    public void setImage(byte[] b){
        this.image=b;
    }

}