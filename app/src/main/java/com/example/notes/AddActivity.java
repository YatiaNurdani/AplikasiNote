package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddActivity extends AppCompatActivity {
    private TextInputLayout edtJudul;
    private TextInputLayout edtDesk;
    private Button btnTambah;
    private DatabaseHelper db;
    private Note note;
    private String judul;
    private String desk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtJudul = findViewById(R.id.inputlayoutjudul);
        edtDesk = findViewById(R.id.inputlayoutdeskripsi);
        btnTambah = findViewById(R.id.btnTambah);
        db = new DatabaseHelper(this);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = edtJudul.getEditText().getText().toString();
                desk = edtDesk.getEditText().getText().toString();

                if (judul.isEmpty() || desk.isEmpty()) {
                    Snackbar.make(view, "Mohon Masukan Datanya !!!", Snackbar.LENGTH_SHORT).show();
                } else {
                    note = new Note();
                    note.setJudul(judul);
                    note.setDeskripsi(desk);
                    db.insert(note);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("status", "add");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

}