package com.e.roomdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewNoteActivity extends AppCompatActivity {
EditText editText;
Button btAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText = findViewById(R.id.et_newnote);
        btAdd = findViewById(R.id.add);

        btAdd.setOnClickListener(new View.OnClickListener() {
            Intent intent = new Intent();

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText())){
                    setResult(0,intent);
                }else {
                    intent.putExtra("note",editText.getText().toString());
                    setResult(1,intent);
                }
                finish();
            }
        });

    }

}
