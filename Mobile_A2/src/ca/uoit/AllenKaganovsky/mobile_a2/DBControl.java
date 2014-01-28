package ca.uoit.AllenKaganovsky.mobile_a2;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBControl extends SQLiteOpenHelper {

	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "NotesDB";
	
    public DBControl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create notes table
        String CREATE_NOTE_TABLE = "CREATE TABLE notes ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "title TEXT, "+
                "content TEXT, "+
                "date TEXT)";
 
        // create notes table
        db.execSQL(CREATE_NOTE_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS notes");
		 
        // create fresh books table
        this.onCreate(db);
	}
	
	public void addNote(Note note) {
		// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", note.getTitle()); // get title 
        values.put("content", note.getContent()); // get author
        values.put("date", note.getDate());
 
        // 3. insert
        db.insert("notes", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        // 4. close
        db.close();
	}
	
	public Note getNote(int id){

	    // 1. get reference to readable DB
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String[] columns = {"id","title","content","date"};
	    
	    // 2. build query
	    Cursor cursor = 
	            db.query("notes", // a. table
	            columns, // b. column names
	            " id = ?", // c. selections 
	            new String[] { String.valueOf(id) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    // 3. if we got results get the first one
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    // 4. build note object
	    Note note = new Note();
	    note.setId(Integer.parseInt(cursor.getString(0)));
	    note.setTitle(cursor.getString(1));
	    note.setContent(cursor.getString(2));
	    //note.setDate(Long.parseLong(cursor.getString(3)));
	    note.setDate(cursor.getString(3));
	 
	    // 5. return note
	    return note;
	}
	
	public List<Note> getAllNotes(int sort) {
	       List<Note> notes = new LinkedList<Note>();
	 
	    // 1. build the query
	       String query = "SELECT * FROM notes ORDER BY title ASC";
			switch(sort) {
			case 0:
				query = "SELECT * FROM notes ORDER BY title ASC";
				break;
			case 1:
				query = "SELECT * FROM notes ORDER BY title DESC";
				break;
			case 2:
				query = "SELECT * FROM notes ORDER BY date ASC";
				break;
			case 3:
				query = "SELECT * FROM notes ORDER BY date DESC";
				break;
			}
			
	       
	       
	 
	       // 2. get reference to writable DB
	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       // 3. go over each row, build book and add it to list
	       Note note = null;
	       if (cursor.moveToFirst()) {
	           do {
	               note = new Note();
	               note.setId(Integer.parseInt(cursor.getString(0)));
	               note.setTitle(cursor.getString(1));
	               note.setContent(cursor.getString(2));
	               note.setDate(cursor.getString(3));
	 
	               // Add book to books
	               notes.add(note);
	           } while (cursor.moveToNext());
	       }
	 
	       // return books
	       return notes;
	   }

	public void updateNote(Note note, String title, String content) {
		 
	    // 1. get reference to writable DB
	    SQLiteDatabase db = this.getWritableDatabase();
	    int id = note.getId();
	 
	    String query = "UPDATE notes SET title='"+title+"', content='"+content+"' WHERE id='"+id+"'";
	    
	    db.execSQL(query);
	 
	    // 4. close
	    db.close();
	}
	
	public void deleteNote(Note note) {
		 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete("notes", //table name
                "id = ?",  // selections
                new String[] { String.valueOf(note.getId()) }); //selections args
 
        // 3. close
        db.close();
 
    }
	
	public void deleteAllNotes() {
		 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.execSQL("DELETE FROM notes");
 
        // 3. close
        db.close();
 
    }
}
