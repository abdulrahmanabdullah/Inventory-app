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
import android.util.Log;
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
 * @author Abdulrahman.A on 16/01/2018.
 */

public class EditInventoryActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent mIntent;
    private Uri currentItemUri;
    private final String TAG = EditInventoryActivity.class.getSimpleName();
    EditText edProductName, edPrice, edQuantity, edSupplierName, edSupplierEmail, edSupplierPhone;
    Button btnIncrement, btnDecrement, btnUpdateItem, btnDeleteItem, btnContact;
    Toolbar toolbar;
    private int quantityCounter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        // initials intent ..
        mIntent = getIntent();
        // Declare EditText .
        edProductName = findViewById(R.id.ed_name);
        edPrice = findViewById(R.id.ed_price);
        edQuantity = findViewById(R.id.ed_quantity);
        edSupplierName = findViewById(R.id.ed_supplier_name);
        edSupplierEmail = findViewById(R.id.ed_supplier_email);
        edSupplierPhone = findViewById(R.id.ed_supplier_phone);
        // Declare Buttons ...
        /**This Button for increment use {@link #incrementQuantity()} method.*/
        btnIncrement = findViewById(R.id.btn_increment);
        btnIncrement.setOnClickListener(this);
        /**This Button for decrement use {@link #decrementQuantity()} method.*/
        btnDecrement = findViewById(R.id.btn_decrement);
        btnDecrement.setOnClickListener(this);
        /** This Button for update current item , use {@link #updateRecord()}  */
        btnUpdateItem = findViewById(R.id.btn_update_item);
        btnUpdateItem.setOnClickListener(this);
        /** This Button for remove one item , use {@link #deleteRecord()} method*/
        btnDeleteItem = findViewById(R.id.btn_delete_item);
        btnDeleteItem.setOnClickListener(this);
        /** This Button for contact the supplier, use {@link #supplierContact()} method */
        btnContact = findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(this);
        // Check uri Not empty,For display and undisplayed buttons .
        currentItemUri = mIntent.getData();
        if (currentItemUri != null) {
            setTitle(R.string.edit_title_page);
            // Add button
            showDetailData();
        }
        // if empty,Change title page,To Add New .
//        else {
//            setTitle(R.string.add_title_page);
//            hiddenButtons();
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update_item:
                updateRecord();
                break;
            case R.id.btn_delete_item:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getString(R.string.alert_mes_when_remove_one_item)).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord();
                        finish();
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog !=null){
                            dialog.dismiss();
                        }
                    }
                }).show();
                break;
            case R.id.btn_contact:
                supplierContact();
                break;
            case R.id.btn_increment:
                incrementQuantity();
                break;
            case R.id.btn_decrement:
                decrementQuantity();
                break;
        }
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
     * Delete current item
     */
    private void deleteRecord() {
        String selection = null;
        String[] selectionArgs = null;
        int rowDeleted = getContentResolver().delete(currentItemUri, selection, selectionArgs);
        Log.i(TAG, "you delete " + rowDeleted);
        Toast.makeText(this, getString(R.string.successful_deleted), Toast.LENGTH_LONG).show();
    }

    /**
     * if {@link #currentItemUri } Not empty get all data for this uri .
     */
    private void showDetailData() {
        Cursor cursor = getContentResolver().query(currentItemUri,
                null,
                null,
                null,
                null);
        if (cursor == null) {
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
//            edQuantity.setText(String.valueOf(_Quantity));
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

    /**
     * This method to set Quantity value in {@link #edQuantity} after updated .
     */
    private void updateQuantity(int process) {
        edQuantity.setText(String.valueOf(process));
    }

    //update one OR more values .
    private void updateRecord() {
        // update by name ...
        String whereName = edProductName.getText().toString();
        String selection = COLUMN_NAME + " =?";
        String[] selectionArgs = {whereName};
        ContentValues values = new ContentValues();
        // Start take new values ... And pass it to the ContentValues ..
        String newName = edProductName.getText().toString();
        if (!TextUtils.isEmpty(newName)){
            values.put(COLUMN_NAME, newName);
        }else{
           Toast.makeText(this,getString(R.string.fill_product_name),Toast.LENGTH_SHORT).show();
           return;
        }
        String newPrice = edPrice.getText().toString();
        if (!TextUtils.isEmpty(newPrice)){
            values.put(COLUMN_PRICE, Integer.valueOf(newPrice));
        }else{
            Toast.makeText(this,getString(R.string.what_price)+newName,Toast.LENGTH_SHORT).show();
            return;
        }
        String newQuantity = edQuantity.getText().toString();
        if (!TextUtils.isEmpty(newQuantity)){
            updateQuantity(Integer.valueOf(newQuantity));
            values.put(COLUMN_QUANTITY, Integer.valueOf(newQuantity));
        }else{
           Toast.makeText(this,getString(R.string.what_quatity),Toast.LENGTH_SHORT).show();
           return;
        }
        String newSupplierName = edSupplierName.getText().toString();
        if (!TextUtils.isEmpty(newSupplierName)){
            values.put(COLUMN_SUPPLIER_NAME, newSupplierName);
        }else{
            Toast.makeText(this,getString(R.string.please_enter_supplier_name),Toast.LENGTH_SHORT).show();
            return;
        }
        String newSupplierEmail = edSupplierEmail.getText().toString();
        if (!TextUtils.isEmpty(newSupplierEmail)){
            values.put(COLUMN_SUPPLIER_EMAIL, newSupplierEmail);
        }else{
            Toast.makeText(this,getString(R.string.please_enter_supplier_email),Toast.LENGTH_SHORT).show();
            return;
        }
        String newSupplierPhone = edSupplierPhone.getText().toString();
        if (!TextUtils.isEmpty(newSupplierPhone)){
            values.put(COLUMN_SUPPLIER_PHONE,Integer.parseInt(newSupplierPhone));
        }else{
            Toast.makeText(this,getString(R.string.please_enter_supplier_phone),Toast.LENGTH_SHORT).show();
            return;
        }
        int rowUpdate = getContentResolver().update(currentItemUri, values, selection, selectionArgs);
        if (rowUpdate == 0) {
            Toast.makeText(this, getString(R.string.failed_update) + selection, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.successful_update) + selectionArgs[0], Toast.LENGTH_LONG).show();
        }
        Log.i(TAG, "Update successful " + rowUpdate);
    }

    // open Phone Dial to call the supplier .
    private void supplierContact() {
        mIntent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = edSupplierPhone.getText().toString();
        mIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (mIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mIntent);
        }
    }
}
