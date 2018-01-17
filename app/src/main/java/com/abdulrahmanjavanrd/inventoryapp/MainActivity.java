package com.abdulrahmanjavanrd.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.activities.DeleteAllActivity;
import com.abdulrahmanjavanrd.inventoryapp.activities.InventoryDetails;
import com.abdulrahmanjavanrd.inventoryapp.adapter.InventoryCursorAdapter;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryDBHelper;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRODUCT_IMAGE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry._ID;

//TODO 1: create list view in main activity . Done
//TODO 2: create item layout . Done
//TODO 3: in item layout contain ProductName ,Price ,quantity,and Sale Button to reduce quantity. Done
//TODO 4: Create Content Provider DOne .
//TODO 5: create new activity , ProductDetailActivity when click any items in the list .
//TODO 6: create list view in main activity .
public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>,NavigationView.OnNavigationItemSelectedListener{


    private final String TAG = MainActivity.class.getSimpleName();
    private final int LOADER = 0 ;
    Toolbar toolbar ;
    ListView listView ;
    InventoryCursorAdapter adapter ;
    Cursor cursor ;

    DrawerLayout drawerLayout ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /** when list is empty ...*/
         listView = findViewById(R.id.list_view_item);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        // init adapter
        adapter = new InventoryCursorAdapter(this,null);
        listView.setAdapter(adapter);
        // set onClickListener.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, InventoryDetails.class);
                startActivity(i);
                Toast.makeText(MainActivity.this,"clicked"+cursor.getString(cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_NAME)),Toast.LENGTH_SHORT).show();
            }
        });

        // Navigation Drawer ..
         drawerLayout = findViewById(R.id.drawer_layout);
         initialDrawer();
        // start loader
        getLoaderManager().initLoader(LOADER,null,this);

    }


    private void initialDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    // Read all records in dataBase.
//    private void displayData(){
//        String[] projection = {
//               _ID,COLUMN_NAME,COLUMN_PRICE,COLUMN_QUANTITY,COLUMN_PRODUCT_IMAGE,COLUMN_SUPPLIER_NAME,COLUMN_SUPPLIER_EMAIL,COLUMN_SUPPLIER_PHONE
//        };
//        String selection = null ;
//        String[] selectionArgs = null ;
//        String sortBy = null ;
////       database = helper.getReadableDatabase();
//        Uri uri = InventoryContract.InventoryEntry.CONTENT_URI ;
//        Log.i(TAG,"uri = "+uri);
//       Cursor cursor = getContentResolver().query(uri,projection,selection,selectionArgs,sortBy);
//       if (cursor != null){
//           String str = "\n";
//           while (cursor.moveToNext()){
//               String[] columns = cursor.getColumnNames();
//               for (String items : columns){
//                   str += "\t"+cursor.getString(cursor.getColumnIndex(items));
//               }
//               str += "\n";
//           }
//           Log.i(TAG," Total records "+ str );
//       }
//
//        ListView listView = findViewById(R.id.list_item);
//        InventoryCursorAdapter adapter = new InventoryCursorAdapter(this, cursor, 0);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
//        });
//    }


    //

    /**
     *Insert data into {@link InventoryDBHelper} data base.
     */
//    private void insertData(){
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME,"Google Pixel XL ");
//        values.put(COLUMN_PRICE,299);
//        values.put(COLUMN_QUANTITY,1);
//        values.put(COLUMN_PRODUCT_IMAGE,"pic:url");
//        values.put(COLUMN_SUPPLIER_NAME,"google.com");
//        values.put(COLUMN_SUPPLIER_EMAIL,"google@google.com");
//        values.put(COLUMN_SUPPLIER_PHONE,4213456);
//        Uri uri = InventoryContract.InventoryEntry.CONTENT_URI ;
//        Uri rowInserted =getContentResolver().insert(uri,values);
//        Log.i(TAG," Inserted "+ rowInserted+" rows ");
//    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID,COLUMN_NAME,COLUMN_PRICE,COLUMN_QUANTITY
        };
        return new CursorLoader(MainActivity.this, InventoryContract.InventoryEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
        cursor = data ;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        switch (item.getItemId()){
            case R.id.nav_add_new:
                //TODO: open new activity here ..
                Toast.makeText(this, "you clicked add ", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_delete_all_items:
                //TODO: open new activity here ..
                Intent i = new Intent(this, DeleteAllActivity.class);
                startActivity(i);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
}
