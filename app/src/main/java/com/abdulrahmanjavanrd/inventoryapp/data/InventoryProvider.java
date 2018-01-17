package com.abdulrahmanjavanrd.inventoryapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.CONENT_AUTHORITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.TABLE_NAME;

/**
 * @author Abdulrahman.A on 16/01/2018.
 */

public class InventoryProvider extends ContentProvider {

    private final String TAG = InventoryProvider.class.getSimpleName();

    private final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH); // result = -1



    // Declare all possible uri accepted .
    private final static int INVENTORY = 0 ;
    private final static int INVENTORY_BY_ID = 1 ;
    private final static int INVENTORY_BY_NAME = 2 ;

    static {
        // declare uri matcher here .
        // this uri like = "com.abdurlahmanjavanrd.inventoryapp.InventoryProvider/inventories , query all data .
        uriMatcher.addURI(CONENT_AUTHORITY, TABLE_NAME, INVENTORY);
        // this uri like = "com.abdurlahmanjavanrd.inventoryapp.InventoryProvider/inventories/1 or 2..etc , query data by id .
        uriMatcher.addURI(CONENT_AUTHORITY, TABLE_NAME + "/#", INVENTORY_BY_ID);
        // this uri like = "com.abdurlahmanjavanrd.inventoryapp.InventoryProvider/inventories/ProductName, query data by Product name .
        uriMatcher.addURI(CONENT_AUTHORITY, TABLE_NAME + "/*", INVENTORY_BY_NAME);
    }

    InventoryDBHelper helper ;
    @Override
    public boolean onCreate() {
        helper = new InventoryDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor ;
        switch (uriMatcher.match(uri)){
            case INVENTORY:
                cursor = database.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case INVENTORY_BY_ID:
                selection = InventoryContract.InventoryEntry._ID +" = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case INVENTORY_BY_NAME:
                selection = InventoryContract.InventoryEntry._ID +" = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                cursor = database.query(TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
                default:
                    throw new IllegalArgumentException("Failed query data");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (uriMatcher.match(uri)){
            case INVENTORY:
            case INVENTORY_BY_ID:
            case INVENTORY_BY_NAME:
                return insertRecord(uri,values,TABLE_NAME);
                default:
                    throw new IllegalArgumentException("Failed insert new data"+uri);
        }
    }

    private Uri insertRecord(Uri uri, ContentValues values, String tableName) {
        SQLiteDatabase database = helper.getWritableDatabase();
        long rowInserted = database.insert(tableName,null,values);
        //Check if failed inserted ..
        if (rowInserted == -1){
            Log.e(TAG,"Failed inserted ");
            return null;
        }else{
            Log.i(TAG,"Successfully inserted");
            // update list after insert data ..
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return ContentUris.withAppendedId(uri,rowInserted);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case INVENTORY:
                return deleteRecord(uri,TABLE_NAME,null,null);
            case INVENTORY_BY_ID:
                selection = InventoryContract.InventoryEntry._ID+" =?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return deleteRecord(uri,TABLE_NAME,selection,selectionArgs);
            case INVENTORY_BY_NAME:
                selection = InventoryContract.InventoryEntry.COLUMN_NAME+" =?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                return deleteRecord(uri,TABLE_NAME,selection,selectionArgs);
                default:
                    throw new IllegalArgumentException("Failed deleted please check uri "+uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private int deleteRecord(Uri uri, String tableName, String selection, String[] selectionArgs) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int rowDel = database.delete(tableName, selection, selectionArgs);

        // Updata list after delete .
        if (rowDel != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.i(TAG, "Deleted it " + rowDel);
        return rowDel;
    }
}
