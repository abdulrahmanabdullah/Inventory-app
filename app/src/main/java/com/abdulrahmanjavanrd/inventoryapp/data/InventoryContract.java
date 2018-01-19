package com.abdulrahmanjavanrd.inventoryapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author Abdulrahman.A 14/01/2018.
 */

public final class InventoryContract {


    // Authority  = package Name + InventoryProvider class
    public static final String CONENT_AUTHORITY = "com.abdulrahmanjavanrd.inventoryapp.InventoryProvider";

    // Base uri it's = content://com.abdulrahmanjavanrd.inventoryapp.InventoryProvider/inventories/21
    private static final Uri  BASE_URI = Uri.parse("content://"+CONENT_AUTHORITY);

    //Table Name .
    public static final String TABLE_NAME ="inventories";

    //Forbidden create new object .
    private InventoryContract(){}

    public static final class InventoryEntry implements BaseColumns {
        //This uri i deal with .
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, TABLE_NAME);
        // id field data type = int
        public static final String _ID = BaseColumns._ID;
        // ProductName field data type = text .
        public static final String COLUMN_NAME = "ProductName";
        // ProductPrice field data type = int.
        public static final String COLUMN_PRICE = "Price";
        //  Quantity field data type = int.
        public static final String COLUMN_QUANTITY = "Quantity";
        // ProductImage field data type = text. // initial image , maybe i use it in future .
        public static final String COLUMN_PRODUCT_IMAGE = "ProductImage";
        // SupplierName field data type = text
        public static final String COLUMN_SUPPLIER_NAME = "SupplierName";
        // SupplierEmail field data type = text.
        public static final String COLUMN_SUPPLIER_EMAIL = "SupplierEmail";
        // SupplierPhone field data type = int .
        public static final String COLUMN_SUPPLIER_PHONE = "SupplierPhone";


        // If Quantity Empty ..
        public static final int DEFAULT_QUANTITY = 5;
        //IF SupplierName Empty ..
        public static final String DEFAULT_SUPPLIER_NAME = "Abdulrahman.A";
        //IF SupplierEmail Empty ..
        public static final String DEFAULT_SUPPLIER_EMAIL = "nfs056@gmail.com";
        // If SupplierPhone Empty ..
        public static final int DEFAULT_SUPPLIER_PHONE = 1234567890;
    }
}
