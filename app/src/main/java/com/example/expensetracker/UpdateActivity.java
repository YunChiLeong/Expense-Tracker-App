package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    String id, name, category, date, amount, note;
    EditText name_input, cat_input, date_input, amount_input, note_input;
    Button update_button, delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name_input = findViewById(R.id.name_update);
        cat_input = findViewById(R.id.category_update);
        date_input = findViewById(R.id.date_update);
        amount_input = findViewById(R.id.amount_update);
        note_input = findViewById(R.id.note_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // Populate editTexts with current data
        getIntentData();

        // set Action Bar title to expense name
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(name);
        }
        // Detect text changes to enable or disable add button
        name_input.addTextChangedListener(inputTextWatcher);
        cat_input.addTextChangedListener(inputTextWatcher);
        date_input.addTextChangedListener(inputTextWatcher);
        amount_input.addTextChangedListener(inputTextWatcher);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                name = name_input.getText().toString().trim();
                category = cat_input.getText().toString().trim();
                date = date_input.getText().toString().trim();
                amount = amount_input.getText().toString().trim();
                note = note_input.getText().toString().trim();
                myDB.updateData(id, name, category, date, amount, note);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConfirmDialog();
            }
        });
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nameInput = name_input.getText().toString().trim();
            String catInput = cat_input.getText().toString().trim();
            String dateInput = date_input.getText().toString().trim();
            String amountInput = amount_input.getText().toString().trim();
            update_button.setEnabled(!nameInput.isEmpty() && !catInput.isEmpty() &&
                    !dateInput.isEmpty() && !amountInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };


    // getIntentData retrieves existing data into Update Activity
    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("category") && getIntent().hasExtra("date") &&
                getIntent().hasExtra("amount")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            category = getIntent().getStringExtra("category");
            date = getIntent().getStringExtra("date");
            amount = getIntent().getStringExtra("amount");
            if(getIntent().hasExtra("note")) {
                note = getIntent().getStringExtra("note");
                note_input.setText(note);
            }else{
                note = null;
            }
            name_input.setText(name);
            cat_input.setText(category);
            date_input.setText(date);
            amount_input.setText(amount);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Makes a delete confirmation pop up. Called by delete_button in UpdateActivity class.
    void deleteConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper myDB = new DatabaseHelper(UpdateActivity.this);
                myDB.deleteOne(id);
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