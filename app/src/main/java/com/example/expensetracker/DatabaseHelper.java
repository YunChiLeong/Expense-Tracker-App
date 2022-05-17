package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ExpenseTracker.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_expenses";
    private static final String COLUMN_ID ="_id";
    private static final String COLUMN_NAME = "expense_name";
    private static final String COLUMN_CATEGORY = "expense_category";
    private static final String COLUMN_DATE = "expense_date";
    private static final String COLUMN_AMOUNT = "expense_amount";
    private static final String COLUMN_NOTE= "expense_note";

    DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_AMOUNT + " FLOAT, " +
                        COLUMN_NOTE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Inserts data into the database, Called by add_button onClick in AddActivity Class
    void addExpense(String name, String category, String date, float amount, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_AMOUNT,amount);
        cv.put(COLUMN_NOTE,note);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed to Add.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Added.", Toast.LENGTH_SHORT).show();
        }
    }

    // Called in storeData function in MainActivity class.
    Cursor readData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        // cursor contains all the data in db returned from getReadableDatabase method
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    // Update new info into database. Called by update_button onClick in UpdateActivity Class
    void updateData(String rowID, String newName, String newCat, String newDate,
                    String newAmount, String newNote){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newName);
        cv.put(COLUMN_CATEGORY, newCat);
        cv.put(COLUMN_DATE, newDate);
        cv.put(COLUMN_AMOUNT, newAmount);
        cv.put(COLUMN_NOTE, newNote);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{rowID});
        if(result == -1){
            Toast.makeText(context,"Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Updated.", Toast.LENGTH_SHORT).show();
        }
    }

    // Take the rowID and delete the row
    void deleteOne(String rowID){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{rowID});
        if(result == -1){
            Toast.makeText(context,"Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Called by the delete all icon on top right corner. Delete all entries in database.
    void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }



}
