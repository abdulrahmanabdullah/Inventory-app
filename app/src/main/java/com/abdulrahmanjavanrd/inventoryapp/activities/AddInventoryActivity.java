package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
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
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.CONTENT_URI;

/**
 * @author Abdurlahman.A on 22/01/2018.
 */

public class AddInventoryActivity extends AppCompatActivity {
    Button btnIncrement, btnDecrement, btnAddNewItem;
    EditText edProductName, edPrice, edQuantity, edSupplierName, edSupplierEmail, edSupplierPhone;
    private int quantityCounter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Item");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Declare EditText .
        edProductName = findViewById(R.id.ed_name);
        edPrice = findViewById(R.id.ed_price);
        edQuantity = findViewById(R.id.ed_quantity);
        edSupplierName = findViewById(R.id.ed_supplier_name);
        edSupplierEmail = findViewById(R.id.ed_supplier_email);
        edSupplierPhone = findViewById(R.id.ed_supplier_phone);
        btnAddNewItem = findViewById(R.id.btn_add_new_item);
        btnAddNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertNewItem();
            }
        });

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
    }

    /**
     * Insert data into dataBase, with what client input
     */
    private void insertNewItem() {
        String name = edProductName.getText().toString();
        String price = edPrice.getText().toString();
        String quantity = edQuantity.getText().toString();
        String supplierName = edSupplierName.getText().toString();
        String supplierEmail = edSupplierEmail.getText().toString();
        String supplierPhone = edSupplierPhone.getText().toString();
        Uri uri = CONTENT_URI;
        ContentValues values = new ContentValues();
        //Check Product Name it's required  than put data ...
        if (contentNotEmpty(name)) {
            values.put(COLUMN_NAME, name);
        } else {
            Toast.makeText(this, getString(R.string.fill_product_name), Toast.LENGTH_LONG).show();
            return;
        }
        // Then check price,any items should contain price.
        if (contentNotEmpty(price)) {
            values.put(COLUMN_PRICE, Integer.parseInt(price));
        } else {
            Toast.makeText(this, getString(R.string.what_price) + name, Toast.LENGTH_LONG).show();
            return;
        }
        // Then check Quantity , if empty , get the Default value,And forward
        if (contentNotEmpty(quantity)) {
            quantityCounter = Integer.parseInt(quantity);
            values.put(COLUMN_QUANTITY, Integer.parseInt(quantity));
        } else {
            Toast.makeText(this, getString(R.string.what_quatity) + name, Toast.LENGTH_LONG).show();
            return;
        }
        // Last Condition check the supplier info,
        if (contentNotEmpty(supplierName)) {
            values.put(COLUMN_SUPPLIER_NAME, supplierName);
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_name), Toast.LENGTH_SHORT).show();
            return;
        }
        if (contentNotEmpty(supplierEmail)) {
            values.put(COLUMN_SUPPLIER_EMAIL, supplierEmail);
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_email), Toast.LENGTH_SHORT).show();
            return;
        }
        if (contentNotEmpty(supplierPhone)) {
            values.put(COLUMN_SUPPLIER_PHONE, Integer.parseInt(supplierPhone));
        } else {
            Toast.makeText(this, getString(R.string.please_enter_supplier_phone), Toast.LENGTH_SHORT).show();
            return;
        }
        // After check all fields, it's okay to send data to database .
        Uri rowUri = getContentResolver().insert(uri, values);
        //Check if add data successful..
        if (rowUri == null) {
            Toast.makeText(this, getString(R.string.failed_add_new_item), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.successful_insert), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param str get text from EditText .
     * @return true if NOT Null, else return false .
     */
    private boolean contentNotEmpty(String str) {
        boolean mHasContent = false;
        if (!TextUtils.isEmpty(str.trim())) {
            mHasContent = true;
        }
        return mHasContent;
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
        if (quantityCounter == 0) {
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
    private void updateQuantity(int quantity) {
        edQuantity.setText(String.valueOf(quantity));
    }
}
