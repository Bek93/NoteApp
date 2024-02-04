package com.beknumonov.noteapp2.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.beknumonov.noteapp2.model.Note;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "NOTES_APP";
    private static Integer DB_VERSION = 1;

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.Entry.createTableQuery());
    }


    public void addNote(Note note, boolean mutiple) {

        //INSERT INTO NOTES (title, content) VALUES ("title 1", "content 1" );
        String insert_sql = "INSERT INTO " + Note.Entry.TABLE_NAME +
                "( " + Note.Entry.TITLE + ", " + Note.Entry.CONTENT + ") " +
                "VALUES ('" + note.getTitle() + "', '" + note.getContent() + "' );";

        Log.d("SQLite", insert_sql);

        if (db == null)
            db = getWritableDatabase();

        db.execSQL(insert_sql);

        if (!mutiple)
            db.close();
    }


    public void addNotes(ArrayList<Note> notes) {
        db = getWritableDatabase();

        for (Note note : notes) {
            addNote(note, true);
        }
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Note.Entry.TABLE_NAME + " ORDER BY CREATED_AT DESC;";

        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {
            Note note = new Note();

            note.setId(cursor.getInt(0));
            note.setTitle(cursor.getString(cursor.getColumnIndex(Note.Entry.TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(Note.Entry.CONTENT)));
            note.setCreatedAt(cursor.getString(cursor.getColumnIndex(Note.Entry.CREATED_AT)));
            notes.add(note);
        }
        cursor.close();
        db.close();


        return notes;
    }

    public void updateNote(Note note) {

        db = getWritableDatabase();
        String updateSql = "UPDATE " + Note.Entry.TABLE_NAME + " SET " + Note.Entry.TITLE + "= '" + note.getTitle() + "', " + Note.Entry.CONTENT + "='" + note.getContent() + "'" +
                " WHERE ID=" + note.getId();
        db.execSQL(updateSql);
        db.close();
    }

    public void deleteNote(Note note) {
        db = getWritableDatabase();
        String deleteSQL = "DELETE FROM " + Note.Entry.TABLE_NAME + " WHERE ID=" + note.getId();
        db.execSQL(deleteSQL);
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
