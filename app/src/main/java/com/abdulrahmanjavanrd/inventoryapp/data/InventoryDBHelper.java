package com.abdulrahmanjavanrd.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry._ID;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.TABLE_NAME;

/**
 * @author Abdulrahman.A on 14/01/2018.
 */

public class InventoryDBHelper  extends SQLiteOpenHelper {

    // DataBase Name .
     private final static String INVENTORY_DATABASE_NAME = "inventory.db";
    // DataBase Version .
    private final static int INVENTORY_DATABASE_V = 1;
    // create new table in inventory.db
    private final static String SQL_CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+
            _ID+" INTEGER  PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME+" TEXT NOT NULL ,"
            + COLUMN_PRICE+" INTEGER , "
            + COLUMN_QUANTITY+" INTEGER NOT NULL ,"
            + COLUMN_SUPPLIER_NAME+ " TEXT ,"
            + COLUMN_SUPPLIER_EMAIL+ " TEXT ,"
            + COLUMN_SUPPLIER_PHONE+ " INTEGER "
            +");";


    // when upgrade database .
    private final static String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    public InventoryDBHelper(Context context){
        super(context, INVENTORY_DATABASE_NAME,null,INVENTORY_DATABASE_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_TABLE);
            onCreate(db);
    }
}
