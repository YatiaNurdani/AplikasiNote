package com.example.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    TextView txtTanggal;
    TextView txtJudul;
    TextView txtDeskripsi;
    private CardView ListNote;
    private Context context;
    private List<Note> noteList;
    private int id;
    private String judul;
    private String desk;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();

        txtTanggal = itemView.findViewById(R.id.txttgl);
        txtJudul = itemView.findViewById(R.id.txtjudul);
        txtDeskripsi = itemView.findViewById(R.id.txtdeskripsi);
        ListNote = itemView.findViewById(R.id.card_list);

        ListNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                alertAction(context, getAdapterPosition());

                return false;
            }
        });
    }

    private void alertAction(Context context, int position) {
        String[] option = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DatabaseHelper db = new DatabaseHelper(context);
        noteList = db.selectNoteData();

        builder.setTitle("Choose option");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    id = noteList.get(position).getId();
                    judul = noteList.get(position).getJudul();
                    desk = noteList.get(position).getDeskripsi();

                    Intent intent = new Intent(context, ModifyActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("judul", judul);
                    intent.putExtra("desk", desk);

                    context.startActivity(intent);

                } else {
                    DatabaseHelper db = new DatabaseHelper(context);
                    db.delete(noteList.get(position).getId());

                    noteList = db.selectNoteData();
                    MainActivity.setupRecyclerView(context, noteList, MainActivity.recyclerView);

                    Toast.makeText(context, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
