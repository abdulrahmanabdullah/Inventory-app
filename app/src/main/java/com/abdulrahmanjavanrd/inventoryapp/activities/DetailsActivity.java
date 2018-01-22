package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class DetailsActivity extends AppCompatActivity {

    private Intent mIntent;
    private Uri currentItemUri;
    private final String TAG = DetailsActivity.class.getSimpleName();
    TextView txvProductName;
    TextView txvPrice;
    TextView txvQuantity;
    TextView txvSupplierName;
    TextView txvSupplierEmail;
    TextView txvSupplierPhone;
    Button btnContact;
    Toolbar toolbar;

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
        txvProductName = findViewById(R.id.txv_name);
        txvPrice = findViewById(R.id.txv_price);
        txvQuantity = findViewById(R.id.txv_quantity);
        txvSupplierName = findViewById(R.id.txv_supplier_name_details);
        txvSupplierEmail = findViewById(R.id.txv_supplier_email_details);
        txvSupplierPhone = findViewById(R.id.txv_supplier_phone_details);
        /** This Button for contact the supplier, use {@link #supplierContact()} method */
        btnContact = findViewById(R.id.btn_contact);
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                supplierContact();
            }
        });

        // Check uri Not empty,Then query the current data .
        currentItemUri = mIntent.getData();
        if (currentItemUri != null) {
            setTitle(R.string.edit_title_page);
            showDetailData();
        } else {
            Toast.makeText(this, getString(R.string.uri_is_empty), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.product_update:
                Intent intent = new Intent(this, UpdateActivity.class);
                //TODO: send uri to update activity .
                intent.setData(currentItemUri);
                startActivity(intent);
                return true;
        }
        return false;
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
            txvProductName.setText(_Name);
            // Quantity
            int _Quantity = cursor.getInt(ColumnQuantity);
            /** save the current quantity value in {@link #quantityCounter} to increment and decrement */
            txvQuantity.setText(String.valueOf(_Quantity));
            // Price
            int _Price = cursor.getInt(ColumnPrice);
            txvPrice.setText(String.valueOf(_Price));
            // Supplier Name
            String supplierName = cursor.getString(ColumnSupplierName);
            txvSupplierName.setText(supplierName);
            // Supplier Email
            String supplierEmail = cursor.getString(ColumnSupplierEmail);
            txvSupplierEmail.setText(supplierEmail);
            // Supplier phone
            int supplierPhone = cursor.getInt(ColumnSupplierPhone);
            txvSupplierPhone.setText(String.valueOf(supplierPhone));
        }
    }

    // open Phone Dial to call the supplier .
    private void supplierContact() {
        mIntent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = txvSupplierPhone.getText().toString();
        mIntent.setData(Uri.parse("tel:" + phoneNumber));
        if (mIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mIntent);
        }
    }
}
