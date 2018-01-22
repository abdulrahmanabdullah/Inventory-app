package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry._ID;

/**
 * @author Abdulrahman.A on 16/01/2018.
 */

public class DeleteAllActivity extends AppCompatActivity {


    Button btnDelete;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_all_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnDelete = findViewById(R.id.btn_delete_all_data);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] projection = {
                        _ID, COLUMN_NAME, COLUMN_PRICE, COLUMN_QUANTITY, COLUMN_SUPPLIER_NAME, COLUMN_SUPPLIER_EMAIL, COLUMN_SUPPLIER_PHONE
                };
                Cursor cursor = getContentResolver().query(InventoryContract.InventoryEntry.CONTENT_URI, projection, null, null, null);
                if (cursor.getCount() == 0) {
                    Toast.makeText(DeleteAllActivity.this, getString(R.string.no_data_to_deleted), Toast.LENGTH_LONG).show();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAllActivity.this);
                    builder.setMessage(R.string.alert_mes_when_remove_data).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAllData();
                            finish();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null)
                                dialog.dismiss();
                        }
                    }).show();
                }
            }
        });
    }

    private void deleteAllData() {
        Uri uri = InventoryContract.InventoryEntry.CONTENT_URI;
        getContentResolver().delete(uri, null, null);
    }
}
