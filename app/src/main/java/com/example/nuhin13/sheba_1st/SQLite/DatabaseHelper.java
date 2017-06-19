package com.example.nuhin13.sheba_1st.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nuhin13.sheba_1st.Informations.Information_for_service_holder;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "sheba";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PHONE + " TEXT," + KEY_EMAIL + " TEXT,"+ KEY_PASSWORD + " TEXT );";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTS);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        onCreate(db);
    }

    // Add User
    public void addUser(String phone, String email, String password) {

        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_PHONE, phone);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        // db.insert(TABLE_USER, null, values);
        // long id = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        long rowInserted = db.insert(TABLE_USER, null, values);
        if(rowInserted != -1)
            Log.d("table 1",  "New row added, row id: " + rowInserted);
            //Toast.makeText(, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Log.d("table 1",  "Wrong" + rowInserted);
    }

    // Get All Users
    public ArrayList<Information_for_service_holder> getAllUsers() {

        ArrayList<Information_for_service_holder> userModelArrayList = new ArrayList<Information_for_service_holder>();

        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Information_for_service_holder userModel = new Information_for_service_holder();
                userModel.setPhone(c.getString(c.getColumnIndex(KEY_PHONE)));
                userModel.setEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));

                userModelArrayList.add(userModel);
                } while (c.moveToNext());
         }
        return userModelArrayList;
    }

    // User LogIn

    public boolean checkUser(String phone, String password) {

        // array of columns to fetch
        String[] columns = {
                KEY_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = KEY_PHONE + " = ?" + " AND " + KEY_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {phone, password};

        // query user table with conditions
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public void updateUser(int id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
       /* ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, name);
        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
       */
    }

    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting from users table
       /* db.delete(TABLE_USER, KEY_ID + " = ?",new String[]{String.valueOf(id)});*/
    }

}

