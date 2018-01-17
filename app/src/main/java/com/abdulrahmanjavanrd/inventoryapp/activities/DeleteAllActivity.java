package com.abdulrahmanjavanrd.inventoryapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.abdulrahmanjavanrd.inventoryapp.R;
import com.abdulrahmanjavanrd.inventoryapp.dialog.DeleteDataDialogFragment;

/**
 * Created by nfs05 on 16/01/2018.
 */

public class DeleteAllActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnDelete ;
    Toolbar toolbar ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_all_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnDelete = findViewById(R.id.btn_delete_all_data);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete_all_data:
                DeleteDataDialogFragment dialog = new DeleteDataDialogFragment();
                dialog.show(getFragmentManager(),"");

        }
    }
}
