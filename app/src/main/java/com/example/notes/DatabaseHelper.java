package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "NoteList";
    private static final String TABLE_NAME = "tbl_note";
    private static final String KEY_ID = "_id";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_JUDUL = "judul";
    private static final String KEY_DESK = "deskripsi";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createNoteTable = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TANGGAL + " TEXT, " + KEY_JUDUL + " TEXT, " + KEY_DESK + " TEXT" + ")";
        sqLiteDatabase.execSQL(createNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String tgl = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        values.put(KEY_TANGGAL, tgl);
        values.put(KEY_JUDUL, note.getJudul());
        values.put(KEY_DESK, note.getDeskripsi());

        db.insert(TABLE_NAME, null, values);
    }

    public List<Note> selectNoteData() {
        ArrayList<Note> notes = new ArrayList<Note>();

        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {KEY_ID, KEY_TANGGAL, KEY_JUDUL, KEY_DESK};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tgl = cursor.getString(1);
            String judul = cursor.getString(2);
            String desk = cursor.getString(3);

            Note note = new Note();
            note.setId(id);
            note.setTanggal(tgl);
            note.setJudul(judul);
            note.setDeskripsi(desk);

            notes.add(note);
        }

        return notes;
    }

    public void update(Note note) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        String tgl = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

        values.put(KEY_TANGGAL, tgl);
        values.put(KEY_JUDUL, note.getJudul());
        values.put(KEY_DESK, note.getDeskripsi());

        String whereClause = KEY_ID + " = '" + note.getId() + "'";

        db.update(TABLE_NAME, values, whereClause, null);
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_ID + " = '" + id + "'";

        db.delete(TABLE_NAME, whereClause, null);
    }

}
