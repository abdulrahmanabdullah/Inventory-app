package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;

/**
 * @author Abdulrahman.A on 16/01/2018.
 */

public class AddAndEditActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = AddAndEditActivity.class.getSimpleName();
    EditText edProductName , edPrice , edQuantity,edSupplierName,edSupplierEmail,edSupplierPhone;
    Button  btnIncrease , btnDecrease ,btnAddNewItem , btnUpdateItem,btnDeleteItem ,btnContact ;
    Toolbar toolbar ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        String titlePage = intent.getStringExtra("title");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titlePage);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Declare EditText .
        edProductName = findViewById(R.id.ed_name);
        edPrice = findViewById(R.id.ed_price);
        edQuantity = findViewById(R.id.ed_quantity);
        edSupplierName = findViewById(R.id.ed_supplier_name);
        edSupplierEmail = findViewById(R.id.ed_supplier_email);
        edSupplierPhone = findViewById(R.id.ed_supplier_phone);

        // Declare Buttons ...
        btnIncrease = findViewById(R.id.btn_increase);
        btnDecrease = findViewById(R.id.btn_decrease);
        btnAddNewItem = findViewById(R.id.btn_add_new_item);
        btnAddNewItem.setOnClickListener(this);
        btnUpdateItem = findViewById(R.id.btn_update_item);
        btnDeleteItem = findViewById(R.id.btn_delete_item);
        btnContact = findViewById(R.id.btn_contact);


        int _id = intent.getIntExtra("id",-1);
        String productName = intent.getStringExtra("name");
        edProductName.setText(productName);
        int price = intent.getIntExtra("price",0);
        edPrice.setText(String.valueOf(price));
        int quantity =intent.getIntExtra("quantity", 0);
        edQuantity.setText(String.valueOf(quantity));
        String supplierName = intent.getStringExtra("supplierName");
        edSupplierName.setText(supplierName);
        String supplierEmail = intent.getStringExtra("supplierEmail");
        edSupplierEmail.setText(supplierEmail);
        int supplierPhone = intent.getIntExtra("supplierPhone", 0);
        edSupplierPhone.setText(String.valueOf(supplierPhone));

        // Check _id  for display and undisplayed buttons .
        if (_id != 0){
            btnAddNewItem.setVisibility(View.GONE);
        }else{
            toolbar.setTitle("Add new Item");
            btnUpdateItem.setVisibility(View.GONE);
            btnDeleteItem.setVisibility(View.GONE);
            btnContact.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_new_item:
                insertNewitem();
                finish();
                break;

        }
    }

    /**
     * Insert data into dataBase, with what client input
     */
    private void insertNewitem(){
        Uri uri = InventoryContract.InventoryEntry.CONTENT_URI;
        String name = edProductName.getText().toString();
        String price = edPrice.getText().toString();
        String quantity =edQuantity.getText().toString();
        String supplierName =edSupplierName.getText().toString();
        String supplierEmail = edSupplierEmail.getText().toString();
        String supplierPhone = edSupplierPhone.getText().toString();
        ContentValues values = new ContentValues();
        values.put(InventoryContract.InventoryEntry.COLUMN_NAME,name);
        values.put(InventoryContract.InventoryEntry.COLUMN_PRICE,Integer.parseInt(price));
        values.put(InventoryContract.InventoryEntry.COLUMN_QUANTITY,Integer.parseInt(quantity));
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME,supplierName);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL,supplierEmail);
        values.put(InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE,Integer.parseInt(supplierPhone));
        Uri rowUri =  getContentResolver().insert(uri,values);
        Log.i(TAG,"inserted data = "+rowUri);
    }
}
