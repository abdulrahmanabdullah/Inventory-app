package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.R;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_PRICE;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_EMAIL;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_SUPPLIER_PHONE;

/**
 * @author Abdulrahman.A  on 1/22/18.
 */

public class UpdateActivity extends AppCompatActivity {

    EditText edProductName;
    EditText edPrice;
    EditText edQuantity;
    EditText edSupplierName;
    EditText edSupplierEmail;
    EditText edSupplierPhone;

    Button btnIncrement;
    Button btnDecrement;
    Button btnUpdateItem;
    Button btnDelete;
    Intent intent;
    private int quantityCounter;

    Uri mCurrentUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Product update");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        intent = getIntent();
        mCurrentUri = intent.getData();
        // Declare EditText
        edProductName = findViewById(R.id.txv_name);
        edPrice = findViewById(R.id.txv_price);
        edQuantity = findViewById(R.id.txv_quantity);
        edSupplierName = findViewById(R.id.txv_supplier_name_details);
        edSupplierEmail = findViewById(R.id.txv_supplier_email_details);
        edSupplierPhone = findViewById(R.id.txv_supplier_phone_details);
        // Declare Button .
        btnIncrement = findViewById(R.id.btn_increment);
        btnIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementQuantity();
            }
        });
        btnDecrement = findViewById(R.id.btn_decrement);
        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementQuantity();
            }
        });
        btnUpdateItem = findViewById(R.id.btn_update_item);
        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update current item::DONE.
                updateItem();
            }
        });
        btnDelete = findViewById(R.id.btn_delete_item);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //::delete the current record .
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                builder.setMessage(getString(R.string.alert_mes_when_remove_one_item)).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord();
                        finish();
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });
        queryDate();
    }

    /**
     * This method to Increment  quantity than update {@link #edQuantity}
     */
    private void incrementQuantity() {
        quantityCounter++;
        updateQuantity(quantityCounter);
    }

    /**
     * This method to decrement  quantity than update {@link #edQuantity}
     * But first check the {@link #quantityCounter} it's not = 0
     */
    private void decrementQuantity() {
        if (quantityCounter <= 0) {
            quantityCounter = 0;
            updateQuantity(quantityCounter);
            Toast.makeText(this, getString(R.string.set_one_at_least), Toast.LENGTH_LONG).show();
        } else {
            quantityCounter--;
            updateQuantity(quantityCounter);
        }
    }

    /**
     * This method to set Quantity value in {@link #edQuantity} after updated .
     */
    private void updateQuantity(int process) {
        edQuantity.setText(String.valueOf(process));
    }

    /**
     * Delete current item
     */
    private void deleteRecord() {
        String selection = null;
        String[] selectionArgs = null;
        getContentResolver().delete(mCurrentUri, selection, selectionArgs);
        Toast.makeText(this, getString(R.string.successful_deleted), Toast.LENGTH_LONG).show();
    }

    // Call this method when onCreate run to extract the current product .
    private void queryDate() {
        Cursor cursor = getContentResolver().query(mCurrentUri, null, null, null, null);
        if (cursor == null) {
            Toast.makeText(this, "Empty data base ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cursor.moveToFirst()) {
            // find column attribute ..
            int ColumnName = cursor.getColumnIndex(COLUMN_NAME);
            int ColumnQuantity = cursor.getColumnIndex(COLUMN_QUANTITY);
            int ColumnPrice = cursor.getColumnIndex(COLUMN_PRICE);
            int ColumnSupplierName = cursor.getColumnIndex(COLUMN_SUPPLIER_NAME);
            int ColumnSupplierEmail = cursor.getColumnIndex(COLUMN_SUPPLIER_EMAIL);
            int ColumnSupplierPhone = cursor.getColumnIndex(COLUMN_SUPPLIER_PHONE);
            // Extract out the values And set Text ..
            String _Name = cursor.getString(ColumnName);
            edProductName.setText(_Name);
            // Quantity
            int _Quantity = cursor.getInt(ColumnQuantity);
            /** save the current quantity value in {@link #quantityCounter} to increment and decrement */
            quantityCounter = _Quantity;
            updateQuantity(_Quantity);
//            txvQuantity.setText(String.valueOf(_Quantity));
            // Price
            int _Price = cursor.getInt(ColumnPrice);
            edPrice.setText(String.valueOf(_Price));
            // Supplier Name
            String supplierName = cursor.getString(ColumnSupplierName);
            edSupplierName.setText(supplierName);
            // Supplier Email
            String supplierEmail = cursor.getString(ColumnSupplierEmail);
            edSupplierEmail.setText(supplierEmail);
            // Supplier phone
            int supplierPhone = cursor.getInt(ColumnSupplierPhone);
            edSupplierPhone.setText(String.valueOf(supplierPhone));
        }
    }

    private void updateItem() {
        ContentValues values = new ContentValues();
        String newName = edProductName.getText().toString();
        if (!TextUtils.isEmpty(newName)) {
            values.put(COLUMN_NAME, newName);
        } else {
            Toast.makeText(this, getString(R.string.fill_product_name), Toast.LENGTH_SHORT).show();
            return;
        }
        String newPrice = edPrice.getText().toString();
        if (!TextUtils.isEmpty(newPrice)) {
            values.put(COLUMN_PRICE, Integer.valueOf(newPrice));
        } else {
            Toast.makeText(this, getString(R.string.what_price) + newName, Toast.LENGTH_SHORT).show();
            return;
        }
        String newQuantity = edQuantity.getText().toString();
        if (!TextUtils.isEmpty(newQuantity)) {
            updateQuantity(Integer.valueOf(newQuantity));
            values.put(COLUMN_QUANTITY, Integer.valueOf(newQuantity));
        } else {
            Toast.makeText(this, getString(R.string.what_quatity), Toast.LENGTH_SHORT).show();
            return;
        }
        String newSupplierName = edSupplierName.getText().toString();
        if (!TextUtils.isEmpty(newSupplierName)) {
            values.put(COLUMN_SUPPLIER_NAME, newSupplierName);
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_name), Toast.LENGTH_SHORT).show();
            return;
        }
        String newSupplierEmail = edSupplierEmail.getText().toString();
        if (!TextUtils.isEmpty(newSupplierEmail)) {
            values.put(COLUMN_SUPPLIER_EMAIL, newSupplierEmail);
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_email), Toast.LENGTH_SHORT).show();
            return;
        }
        String newSupplierPhone = edSupplierPhone.getText().toString();
        if (!TextUtils.isEmpty(newSupplierPhone)) {
            values.put(COLUMN_SUPPLIER_PHONE, Integer.parseInt(newSupplierPhone));
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_phone), Toast.LENGTH_SHORT).show();
            return;
        }
        String selection = COLUMN_NAME + " =?";
        String whereUpdate = edProductName.getText().toString();
        String[] selectionArgs = {whereUpdate};
        int rowUpdate = getContentResolver().update(mCurrentUri, values, selection, selectionArgs);
        if (rowUpdate == 0) {
            Toast.makeText(this, getString(R.string.failed_update) + selection, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.successful_update) + selectionArgs[0], Toast.LENGTH_LONG).show();
        }
    }
}
