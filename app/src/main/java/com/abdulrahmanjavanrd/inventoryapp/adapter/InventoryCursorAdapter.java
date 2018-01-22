package com.abdulrahmanjavanrd.inventoryapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;

import java.text.NumberFormat;

import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_NAME;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_QUANTITY;
import static com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract.InventoryEntry.CONTENT_URI;

/**
 * @author Abdulrahman.A on 16/01/2018.
 */

public class InventoryCursorAdapter extends CursorAdapter implements View.OnClickListener {


    // To call getContentResolver .
    private Context context;

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_products, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        final TextView productName = view.findViewById(R.id.txv_product_name_value);
        TextView productPrice = view.findViewById(R.id.txv_product_price_value);
        final TextView productQuantity = view.findViewById(R.id.txv_product_quantity_value);
        // first fetch column name ..
        int columnProductName = cursor.getColumnIndex(COLUMN_NAME);
        int columnProductPrice = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
        int columnProductQuantity = cursor.getColumnIndex(COLUMN_QUANTITY);
        // save values .
        String _Name = cursor.getString(columnProductName);
        int _Price = cursor.getInt(columnProductPrice);
        // Add $ to the Price
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        String formatter = numberFormat.format(_Price);
        // save quantity ..
        int _Quantity = cursor.getInt(columnProductQuantity);
        // set result in TextView .
        productName.setText(_Name);
        productPrice.setText(formatter);
        productQuantity.setText(String.valueOf(_Quantity));
        Button btn = view.findViewById(R.id.btn_sale);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** What i did here
                 * Save value as String of result the {@link #updateQuantity(String)}
                 * then Create new variable to tell the getContentResolver WHERE the value you want to updated.
                 *  I Want update By {@link COLUMN_NAME}
                 * After that call the {@link context} to call ContentProvider and update the quantity values  .
                 * */
                String str = updateQuantity(productQuantity.getText().toString());
                productQuantity.setText(str);
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_QUANTITY, Integer.parseInt(str));
                String selection = COLUMN_NAME + " =?";
                String whereName = productName.getText().toString();
                String[] selectionArgs = {whereName};
                context.getContentResolver().update(CONTENT_URI, contentValues, selection, selectionArgs);
            }
        });

    }

    /**
     * First take the _Quantity value and check it's equal the minimum number,
     * If yes set the _Quantity = 1 , No decrement _Quantity .
     *
     * @param q = current value of quantity than converted to  String.valueOf(_Quantity value )
     * @return the int of @param q
     */
    private String updateQuantity(String q) {
        int qu = Integer.valueOf(q);
        if (qu <= 0) {
            qu = 0;
            Toast.makeText(context, context.getString(R.string.set_one_at_least), Toast.LENGTH_SHORT).show();
        } else {
            qu--;
        }
        return String.valueOf(qu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sale:
                break;
        }
    }
}
