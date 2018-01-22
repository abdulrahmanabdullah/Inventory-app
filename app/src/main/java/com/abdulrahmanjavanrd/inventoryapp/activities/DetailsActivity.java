package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.adapter.InventoryCursorAdapter;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry._ID;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener {


    private final String TAG = DetailsActivity.class.getSimpleName();
    private final int LOADER_TASK = 0;
    Toolbar toolbar;
    ListView listView;
    InventoryCursorAdapter adapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* when list is empty ...*/
        listView = findViewById(R.id.list_view_item);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);
        /* initialize  adapter*/
        adapter = new InventoryCursorAdapter(this, null);
        listView.setAdapter(adapter);
        /* set onItemClickListener with CursorAdapter class */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(DetailsActivity.this, EditInventoryActivity.class);
                Uri uri = Uri.withAppendedPath(InventoryContract.InventoryEntry.CONTENT_URI, String.valueOf(id));
                Log.i(TAG, "onItemClick: uri = " + uri);
                mIntent.setData(uri);
                startActivity(mIntent);
            }
        });
        /** Declare Drawer Id then call {@link #initialDrawer()} */
        drawerLayout = findViewById(R.id.drawer_layout);
        initialDrawer();
        // start loader
        getLoaderManager().initLoader(LOADER_TASK, null, this);
        // fab button to add new items ..
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_insert_data);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(DetailsActivity.this, AddInventoryActivity.class);
                startActivity(mIntent);
            }
        });
    }

    /**
     * Create ActionBarDrawer and NavigationView
     */
    private void initialDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    // Start Loader
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                _ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_SUPPLIER_NAME, COLUMN_SUPPLIER_EMAIL, COLUMN_SUPPLIER_PHONE
        };
        return new CursorLoader(DetailsActivity.this, InventoryContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
    // End Loader

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_delete_all_items:
                Intent mIntent = new Intent(this, DeleteAllActivity.class);
                startActivity(mIntent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
