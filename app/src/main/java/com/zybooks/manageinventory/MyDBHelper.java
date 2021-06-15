package com.zybooks.manageinventory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class MyDBHelper extends SQLiteOpenHelper {

    // Setting up all variables that make up the columns in the database.
    private Context context;
    private static final String DATABASE_NAME = "InventoryList.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_list";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "item_name";
    private static final String COLUMN_OWNER = "item_owner";
    private static final String COLUMN_NUM = "item_num";


    MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    //Initializing the Database
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT, " +
                        COLUMN_OWNER + " TEXT, " +
                        COLUMN_NUM + " INTEGER);";
        sqLiteDatabase.execSQL(query);



    }

    @Override
    //This will make sure to erase the table if one already exists.
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    //This method is adding functionality to add the item name, owner and number of items.
    void addItem(String itemName, String itemOwner, int numItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, itemName);
        cv.put(COLUMN_OWNER, itemOwner);
        cv.put(COLUMN_NUM, numItem);
        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Item Added to Inventory!", Toast.LENGTH_SHORT).show();
        }
    }

    //This method sets up the functionality to search the entire database and read the data.
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
           cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    //This method allows the user to update the item's data. Values that will be updated are item name, owner and number of items.
    void updateData(String row_id, String item_name, String item_owner, String item_num) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, item_name);
        cv.put(COLUMN_OWNER, item_owner);
        cv.put(COLUMN_NUM, item_num);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

//        if (result == -1) {
//            Toast.makeText(context, "Well, that didn't work", Toast.LENGTH_SHORT).show(); //PENDING: May not need this toast message.
//        }
//        else {
//            Toast.makeText(context, "The Update was a Success!", Toast.LENGTH_SHORT).show();  //PENDING: May not need this toast message.
//        }
    }

    void deleteItem(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

//        if (result == -1) {
//            Toast.makeText(context, "Data Could Not Be Deleted! It's Still Here!", Toast.LENGTH_SHORT).show();  //PENDING: May not need this toast message.
//        }
//        else {
//            Toast.makeText(context, "The Data is No More!", Toast.LENGTH_SHORT).show();   //PENDING: May not need this toast message.
//        }
    }

    void destroyInventory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
