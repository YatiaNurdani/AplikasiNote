package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    static RecyclerView recyclerView;
    private RecyclerviewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupRecyclerView();

        try {
            Intent intent = getIntent();
            String status = intent.getExtras().getString("status");

            if (status.equals("add")) {
                Toast.makeText(this, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            } else if (status.equals("edit")) {
                Toast.makeText(this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();
            } else if (status.equals("delete")) {
                Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            if (noteList.isEmpty()) {
                Toast.makeText(this, "Klik fab untuk menambah note", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Tekan note untuk opsi lain", Toast.LENGTH_LONG).show();
            }
        }

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

    }

    static void setupRecyclerView(Context context, List<Note> noteList, RecyclerView recyclerView) {
        DatabaseHelper db = new DatabaseHelper(context);
        noteList = db.selectNoteData();

        RecyclerviewAdapter adapter = new RecyclerviewAdapter(noteList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(this);
        noteList = db.selectNoteData();

        adapter = new RecyclerviewAdapter(noteList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}