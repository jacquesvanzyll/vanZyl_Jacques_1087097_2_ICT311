package com.bignerdranch.android.testing1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {

    //Setting Databsae name
    private static final String DATABASE_NAME = "activityManager";

    //Setting Database version
    private static final int DATABASE_VERSION = 1;

    //Settings name of contacts table
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_SETTINGS = "settings";

    //Settings contacts table column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "date";
    private static final String KEY_ACTTYPE = "actType";
    private static final String KEY_PLACE = "place";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_PHOTO = "photo";

    //Settings settings table column names
    private static final String KEY_ID2 = "id2";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_COMMENT2 = "comment2";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID +" ID PRIMARY KEY,"+
                KEY_TITLE +" TITLE," + KEY_DATE +" DATE," + KEY_ACTTYPE +" ACTTYPE,"+
                KEY_PLACE +" PLACE,"+ KEY_DURATION +" DURATION,"
                + KEY_COMMENT +" COMMENT,"+ KEY_PHOTO +" PHOTO" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);

        String CREATE_TABLE_SETTINGS="CREATE TABLE " + TABLE_SETTINGS + "(" + KEY_ID2 +" ID PRIMARY KEY,"+
                KEY_NAME +" TITLE," + KEY_EMAIL +" DATE," + KEY_GENDER +" ACTTYPE,"+
                KEY_COMMENT2 + ")";
        db.execSQL(CREATE_TABLE_SETTINGS);
    }

    // Upgrading database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Deleting older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);


        // Creating newer table
        onCreate(db);
    }

    //Put value into table Contacts
    public void addContacts(Activity activity){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_ID, activity.getID());
        values.put(KEY_TITLE, activity.getTitle());
        values.put(KEY_DATE, activity.getDate());
        values.put(KEY_ACTTYPE, activity.getActivityType());
        values.put(KEY_PLACE, activity.getPlace());
        values.put(KEY_DURATION, activity.getDuration());
        values.put(KEY_COMMENT, activity.getComment());
        values.put(KEY_PHOTO, activity.getImage() );

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }
    public List<Activity> getAllContacts() {
        List<Activity> contactList = new ArrayList<Activity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Activity contact = new Activity();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setTitle(cursor.getString(1));
                contact.setDate(cursor.getString(2));
                contact.setActivityType(cursor.getString(3));
                contact.setPlace(cursor.getString(4));
                contact.setDuration(Integer.parseInt(cursor.getString(5)));
                contact.setComment(cursor.getString(6));
                contact.setImage(cursor.getBlob(7));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    public String getContact(String id) {
        Cursor cursor = null;
        String info = "";
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT * FROM contacts WHERE id=?", new String[] {id + ""});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                info = cursor.getString(cursor.getColumnIndex("*"));
            }
            return info;
        }finally {
            cursor.close();
        }
    }

    public int updateContact(Activity contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, contact.getTitle());
        values.put(KEY_DATE, contact.getDate());
        values.put(KEY_ACTTYPE, contact.getActivityType());
        values.put(KEY_PLACE, contact.getPlace());
        values.put(KEY_DURATION, contact.getDuration());
        values.put(KEY_COMMENT, contact.getComment());
        values.put(KEY_PHOTO, contact.getImage());


        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public int updateSettings(Setting setting, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID2, setting.get_id());
        values.put(KEY_NAME, setting.getName());
        values.put(KEY_EMAIL, setting.getEmail());
        values.put(KEY_GENDER, setting.getGender());
        values.put(KEY_COMMENT, setting.getComment());


        // updating row
        return db.update(TABLE_SETTINGS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }


    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }


}