package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText name_input, cat_input, date_input, amount_input, note_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name_input = findViewById(R.id.name);
        cat_input = findViewById(R.id.category);
        date_input = findViewById(R.id.date);
        amount_input = findViewById(R.id.amount);
        note_input = findViewById(R.id.note);
        add_button = findViewById(R.id.add_button);

        // Detect text changes to enable or disable add button
        name_input.addTextChangedListener(inputTextWatcher);
        cat_input.addTextChangedListener(inputTextWatcher);
        date_input.addTextChangedListener(inputTextWatcher);
        amount_input.addTextChangedListener(inputTextWatcher);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDB = new DatabaseHelper(AddActivity.this);
                myDB.addExpense(name_input.getText().toString().trim(),cat_input.getText().toString().trim()
                        ,date_input.getText().toString().trim(), Float.parseFloat(amount_input.getText().toString().trim())
                        ,note_input.getText().toString().trim());
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
            add_button.setEnabled(!nameInput.isEmpty() && !catInput.isEmpty() &&
                    !dateInput.isEmpty() && !amountInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };
}