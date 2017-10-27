package com.bignerdranch.android.testing1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import static com.bignerdranch.android.testing1.R.id.etPlace;

public class SecondaryActivity extends FragmentActivity {


    private DataBaseHandler db;

    //Creating edit text fields for variables to be displayed and entered into
    private EditText etTitle;
    private EditText etDate;
    private EditText etDuration;
    private EditText etComment;

    //Creating imageview for displaying image from selected listView item
    private ImageView imageView;

    //Creating radiobuttons for selecting activity type
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;

    //Creating variable for ID creation in addContact()
    private int x;

    //Creatings strings that are created from editTexts
    private String sID;
    private String sTitle;
    private String sDate;
    private String sActType;
    private String sPlace;
    private int sDuration;
    private String sComment;

    //private dataAdapter data;
    private Activity dataModel;
    private Bitmap bp;
    private byte[] photo;

    private Googlemap map;

    //Receiving ID of selected item from listView
    Intent intent = getIntent();
    int itemID = Integer.parseInt(intent.getStringExtra("item"));

    //Creating variable for location stored as String
    String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container2);
        if (fragment == null){
            fragment = new SecondaryFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container2, fragment)
                    .commit();
        }

        db = new DataBaseHandler(this);
        //Calling the display of information from selected listView item
        displayInfo();
        //Determining location
        getLocation();

    }

    //Calculating location
    private String getLocation() {

        //Creating variable for location stored as String
        String location = location = map.getMyLocation().getLongitude()+map.getMyLocation().getLongitude();

        return location;
    }

    //Reading values from editTexts
    private void getValues() {

        //Reading value from editTexts and storing them in Strings
        sTitle = etTitle.getText().toString();
        sDate = etDate.getText().toString();
        sDuration = Integer.parseInt(etDuration.getText().toString());
        sComment = etComment.getText().toString();

        //Calculating which activity type is selected
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);

        //Calculating which activity type is selected
        if (rb1.isSelected()) {
            sActType = "Sport";
        } else if (rb2.isSelected()) {
            sActType = "Leisure";
        } else if (rb3.isSelected()) {
            sActType = "Study";
        } else if (rb4.isSelected()) {
            sActType = "Work";
        }
        //Calculating the current location
        sPlace = getLocation();

    }

    //Creating cariable for requesting image capture
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Function invokes intent for photo capture.
    private void launchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Making sure there is an app that can handle my intent

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //Proceed with photo capture intent
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Receives bitmap image from camera after it is taken and displays in imageView
    protected void onActivityResult(int qPass, int ansPass, Intent data) {

        if (qPass == REQUEST_IMAGE_CAPTURE && ansPass == RESULT_OK) {

            Bundle additionals = data.getExtras();
            Bitmap imageBitmap = (Bitmap) additionals.get("data");

            //Displaying image on imageView
            imageView.setImageBitmap(imageBitmap);


        }

    }

    //Adding contact to database
    private void addContact() {

        //Reading values that should be added
        getValues();

        //converting img to byte in order to put it into databse
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] pic = baos.toByteArray();
        ContentValues contval = new ContentValues();
        contval.put("picture", pic);

        //Adding contacts into database "db"
        db.addContacts(new Activity(x, sTitle, sDate, sActType, sPlace, sDuration, sComment, pic));
        //Success message
        Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_LONG).show();
        //Variable for calculating ID
        x++;
    }

    private void updateContact() {

        db.updateContact(Activity , Integer.parseInt(sID));

    }



    //Displaying information onto editTexts from selected listView item
    private void displayInfo(){

        //Selecting the appropriate entry from database
        String selectQuery = "SELECT " + itemID + " FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Creating new contact and assigning values from database
        Activity contact = new Activity();

        //Assigning values to object
        contact.setID(Integer.parseInt(cursor.getString(0)));
        contact.setTitle(cursor.getString(1));
        contact.setDate(cursor.getString(2));
        contact.setActivityType(cursor.getString(3));
        contact.setPlace(cursor.getString(4));
        contact.setDuration(Integer.parseInt(cursor.getString(5)));
        contact.setComment(cursor.getString(6));
        contact.setImage(cursor.getBlob(7));

        //Displaying values in EditTexts
        etTitle.setText(contact.getID());
        etDate.setText(contact.getDate());
        etPlace.setText(contact.getPlace());
        etDuration.setText(contact.getDuration());
        etComment.setText(contact.getComment());
    }

    @Override
    public void onBackPressed() {
        addContact();
        updateContact();
    }

    public void showMap(){

    }



    //Button listener
    public void buttonClicked(View v){
        int id=v.getId();

        switch(id){

            //If the user clicked save:
            case R.id.bSave:
                //Making sure there is something entered into the fields
                if(etTitle.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"The title is currently empty, please fill in the data fields", Toast.LENGTH_LONG).show();
                }  else{
                    //Launching addContact to add item into the database
                    addContact();
                    updateContact();
                }
                //Returning to the Main Activity
                startActivity(new Intent (getApplicationContext(),MainActivity.class));
                break;

            //If the user clicked cancel:
            case R.id.bCancel:
                //Returning to the Main Activity
                startActivity(new Intent (getApplicationContext(),MainActivity.class));
                break;
            //If the user clicked photo:
            case R.id.bPhoto:
                launchTakePictureIntent();
                break;

            //If the user clicked delete:
            case R.id.bDelete:
                //Launching deleteContact to delete the entry
                db.deleteContact(itemID);
                //Returning to the Main Activity
                startActivity(new Intent (getApplicationContext(),MainActivity.class));
                break;

            case R.id.bLoc:
                showMap();
                break;
        }
    }
}
