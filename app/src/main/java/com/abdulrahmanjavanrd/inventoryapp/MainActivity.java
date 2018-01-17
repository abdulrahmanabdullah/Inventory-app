package com.abdulrahmanjavanrd.inventoryapp;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.activities.DeleteAllActivity;
import com.abdulrahmanjavanrd.inventoryapp.activities.AddAndEditActivity;
import com.abdulrahmanjavanrd.inventoryapp.adapter.InventoryCursorAdapter;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryDBHelper;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
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
                cursor =(Cursor) parent.getItemAtPosition(position);
                int _id = cursor.getInt(cursor.getColumnIndex(_ID));
                String productName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                int _price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                int _quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
                String _supplierName = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_NAME));
                String _supplierEmail=  cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_EMAIL));
                int _supplierPhone = cursor.getInt(cursor.getColumnIndex(COLUMN_SUPPLIER_PHONE));
                Intent i = new Intent(MainActivity.this, AddAndEditActivity.class);
                //First send title page ;
                i.putExtra("title","Edit");
                i.putExtra("id",_id);
                i.putExtra("name",productName);
                i.putExtra("price",_price);
                i.putExtra("quantity",_quantity);
                i.putExtra("supplierName",_supplierName);
                i.putExtra("supplierEmail",_supplierEmail);
                i.putExtra("supplierPhone",_supplierPhone);
                startActivity(i);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID,COLUMN_NAME,COLUMN_PRICE,COLUMN_QUANTITY,COLUMN_SUPPLIER_NAME,COLUMN_SUPPLIER_EMAIL,COLUMN_SUPPLIER_PHONE
        };
        return new CursorLoader(MainActivity.this, InventoryContract.InventoryEntry.CONTENT_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent mIntent ;
        switch (item.getItemId()){
            case R.id.nav_add_new:
                //TODO: open new activity here ..
                mIntent = new Intent(this,AddAndEditActivity.class);
                //First send title page
                mIntent.putExtra("title","Add new Item");
//                mIntent.putExtra("id",0);
                startActivity(mIntent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case R.id.nav_delete_all_items:
                //TODO: open new activity here ..
                mIntent = new Intent(this, DeleteAllActivity.class);
                startActivity(mIntent);
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
