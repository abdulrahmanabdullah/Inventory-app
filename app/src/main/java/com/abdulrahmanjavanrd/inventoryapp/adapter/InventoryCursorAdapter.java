package com.abdulrahmanjavanrd.inventoryapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.data.InventoryContract;

/**
 * @author Abdulrahman.A on 16/01/2018.
 */

public class InventoryCursorAdapter extends CursorAdapter implements View.OnClickListener {
    private int decrementQuantity;

    private TextView productQuantity ;

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_products,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {

        TextView productName = view.findViewById(R.id.txv_product_name_value);
        TextView productPrice = view.findViewById(R.id.txv_product_price_value);
         productQuantity = view.findViewById(R.id.txv_product_quantity_value);

        // first fetch column name ..
        int columnProductName = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_NAME);
        int columnProductPrice = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
        int columnProductQuantity = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

        // save values .
        String _Name = cursor.getString(columnProductName);
        int _Price = cursor.getInt(columnProductPrice);
        int _Quantity = cursor.getInt(columnProductQuantity);

        //TODO: update quantity ..
        // set result in TextView .
        productName.setText(_Name);
        productPrice.setText(String.valueOf(_Price));
        productQuantity.setText(String.valueOf(_Quantity));
        Button btn = view.findViewById(R.id.btn_sale);
        //TODO: decrease quantity .
        decrementQuantity = _Quantity;
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                decrementQuantity-- ;
//                updateQuantity(decrementQuantity);
//            }
//        });
    }

    private void updateQuantity(int q){
       productQuantity.setText(String.valueOf(q));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sale:
                break;
        }
    }

}
