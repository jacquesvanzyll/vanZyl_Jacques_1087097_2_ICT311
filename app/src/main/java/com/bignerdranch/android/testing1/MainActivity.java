package com.bignerdranch.android.testing1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.value;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    //Identifying the listview from XML file
    ListView listView1 = (ListView) findViewById(R.id.listView1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container1);
        if (fragment == null){
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container1, fragment)
                    .commit();
        }

        setContentView(R.layout.activity_main);
        //Calling launch of secondary activity
        launchSecondaryActivity();
        //Calling launch of tertiary activity
        launchThirdActivity();
        //Calling the listview population function
        displayList(SQLiteDatabase db);


    }

    // Launching second activity to input data
    private void launchSecondaryActivity() {
         Button mlogButton = (Button) findViewById(R.id.logButton);
         mlogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent (getApplicationContext(),SecondaryActivity.class));
            }

        });
    }

    // Launching tertiary activity to view settings and user information
    private void launchThirdActivity() {
        Button msettingsButton = (Button) findViewById(R.id.settingsButton);
        msettingsButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent (getApplicationContext(),ThirdActivity.class));
            }

        });
    }

    // Launching secondary activity with the ID of the selected item in the viewlist for reference
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //Launching secondary Activity with intent
        Intent intent = new Intent(MainActivity.this,SecondaryActivity.class);
        //Using outExtra to transfer correct item ID to the secondary activity
        intent.putExtra("ID",parent.getSelectedItem().toString().charAt(0));
        startActivity(intent);
    }

    //Populating list from database
    protected void displayList(SQLiteDatabase db) {
        //Select all from database with query
        final String selectQuery = "SELECT * FROM contacts";
        //Starting cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        //Creating myList, which will be used to populate vieList
        List<String> myList = new ArrayList();

        //Looping through database and adding title, date and place to myList
        while (cursor.moveToNext()) {
            //Adding title, date and place to myList
            myList.add (cursor.getString(cursor.getColumnIndex("title")));
            myList.add (cursor.getString(cursor.getColumnIndex("date")));
            myList.add (cursor.getString(cursor.getColumnIndex("place")));
        }

        //Creating adapter in order to populate list, accepting myList created above
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, R.id.listView1, myList);
        //Setting listView adapter to adapter created above in order to populate list
        listView1.setAdapter(adapter);
    }

}
