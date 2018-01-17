package com.abdulrahmanjavanrd.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Abdulrahman.A 14/01/2018.
 */

public final class InventoryContract {


    // Authority  = package Name + InventoryProvider class
    public static final String CONENT_AUTHORITY = "com.abdulrahmanjavanrd.inventoryapp.InventoryProvider";

    // Base uri it's = content://com.abdulrahmanjavanrd.inventoryapp.InventoryProvider
    private static final Uri  BASE_URI = Uri.parse("content://"+CONENT_AUTHORITY);

    //Table Name .
    public static final String TABLE_NAME ="inventories";

    //Forbidden create new object .
    private InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,TABLE_NAME);
        // id field data type = int
        public static final String _ID =BaseColumns._ID;
        // ProductName field data type = text .
        public static final String COLUMN_NAME = "ProductName";
        // ProductPrice field data type = int.
        public static final String COLUMN_PRICE = "Price";
        //  Quantity field data type = int.
        public static final String COLUMN_QUANTITY = "Quantity";
        // ProductImage field data type = text.
        public static final String COLUMN_PRODUCT_IMAGE = "ProductImage";
        // SupplierName field data type = text
        public static final String COLUMN_SUPPLIER_NAME = "SupplierName";
        // SupplierEmail field data type = text.
        public static final String COLUMN_SUPPLIER_EMAIL = "SupplierEmail";
        // SupplierPhone field data type = int .
        public static final String COLUMN_SUPPLIER_PHONE = "SupplierPhone";



    }
}
