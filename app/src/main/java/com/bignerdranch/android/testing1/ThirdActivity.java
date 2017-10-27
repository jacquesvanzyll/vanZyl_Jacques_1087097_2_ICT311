package com.bignerdranch.android.testing1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import static android.R.attr.id;
import static android.os.Build.ID;
import static com.bignerdranch.android.testing1.R.id.etComment;
import static com.bignerdranch.android.testing1.R.id.etDate;
import static com.bignerdranch.android.testing1.R.id.etDuration;
import static com.bignerdranch.android.testing1.R.id.etPlace;
import static com.bignerdranch.android.testing1.R.id.etTitle;
import static com.bignerdranch.android.testing1.R.id.sEmail;
import static com.bignerdranch.android.testing1.R.id.sID;
import static com.bignerdranch.android.testing1.R.id.sName;

public class ThirdActivity extends FragmentActivity {

    private EditText sID;
    private EditText sName;
    private EditText sEmail;
    private EditText sGender;
    private EditText sComment;

    private int ID;
    private String Name;
    private String Email;
    private String Gender;
    private String Comment;

    private DataBaseHandler db;


    private void getValues() {

        //Reading value from editTexts and storing them in Strings
        ID = Integer.parseInt(sID.getText().toString());
        Name = sName.getText().toString();
        Email = sEmail.getText().toString();
        Gender = sGender.getText().toString();
        Comment = sComment.getText().toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container3);
        if (fragment == null){
            fragment = new SettingsFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container3, fragment)
                    .commit();
        }
        //Calls launch of main activity

        launchMainActivity();
        displayInfo();
        getValues();

    }

    private void displayInfo(){

        //Selecting the appropriate entry from database
        String selectQuery = "SELECT " + ID + " FROM " + TABLE_CONTACTS2;

        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor cursor = db2.rawQuery(selectQuery, null);

        //Creating new contact and assigning values from database
        Setting contact = new Setting();

        //Assigning values to object
        contact.set_id(Integer.parseInt(cursor.getString(0)));
        contact.setName(cursor.getString(1));
        contact.setEmail(cursor.getString(2));
        contact.setGender(cursor.getString(3));
        contact.setComment(cursor.getString(4));

        //Displaying values in EditTexts
        sID.setText(contact.get_id());
        sName.setText(contact.getName());
        sEmail.setText(contact.getEmail());
        sGender.setText(contact.getGender());
        sComment.setText(contact.getComment());
    }

    private void launchMainActivity() {
        Button mlogButton = (Button) findViewById(R.id.button2);
        mlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateSettings();
                startActivity(new Intent (getApplicationContext(),MainActivity.class));
            }

        });


    }

    private void updateSettings() {

        db.updateSettings(Setting , ID);

    }
}