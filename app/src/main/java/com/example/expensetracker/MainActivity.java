package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView emptyTV;
    DatabaseHelper myDB;
    ArrayList<String> expense_id, expense_name, expense_category, expense_date, expense_amount, expense_note;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_img);
        emptyTV = findViewById(R.id.empty_tv);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(MainActivity.this);
        expense_id = new ArrayList<>();
        expense_name = new ArrayList<>();
        expense_category = new ArrayList<>();
        expense_date= new ArrayList<>();
        expense_amount= new ArrayList<>();
        expense_note = new ArrayList<>();

        storeData();

        customAdapter = new CustomAdapter(MainActivity.this, this,  expense_id,
                expense_name, expense_category, expense_date, expense_amount, expense_note);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    // Refresh main layout after an update
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    // storeData puts data in each column in db into arrays
    void storeData(){
        Cursor cursor = myDB.readData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            emptyTV.setVisibility(View.VISIBLE);
        }else{
            while(cursor.moveToNext()){
                expense_id.add(cursor.getString(0));
                expense_name.add(cursor.getString(1));
                expense_category.add(cursor.getString(2));
                expense_date.add(cursor.getString(3));
                expense_amount.add(cursor.getString(4));
                expense_note.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
            emptyTV.setVisibility(View.GONE);
        }
    }

    // Delete icon on the top right corner
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            deleteAllConfirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // Pop up message after delete all icon is selected
    void deleteAllConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Confirmation");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper myDB = new DatabaseHelper(MainActivity.this);
                myDB.deleteAll();
                // restart Main Activity to show change
                Intent intent = new Intent (MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        builder.create().show();
    }

}