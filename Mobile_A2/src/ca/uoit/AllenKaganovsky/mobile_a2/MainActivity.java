package ca.uoit.AllenKaganovsky.mobile_a2;

import java.util.*;

import android.os.*;
import android.app.*;
import android.content.*;
import android.graphics.PorterDuff;
import android.support.v4.app.NavUtils;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class MainActivity extends Activity implements OnItemClickListener {

	AlertDialog sortingDialog;
	int sortingMethod = 0;
	List<Note> notes;
	NoteAdapter<Note> adapter;
	DBControl dbc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    sortingMethod = sharedPreferences.getInt("sortingMethod", 0);
	    
	    dbc = new DBControl(this);
	    
	    notes = dbc.getAllNotes(sortingMethod);
	    
	    if (notes.size() == 0) {
	    	Note sample = new Note("Welcome to my App!", "This is a placeholder note!\n\nTo delete it, create a new note - THEN delete this note\n\nThis note will re-appear when the notes are empty.");
	    	dbc.addNote(sample);
	    	notes.add(sample);
	    }
	    
	    //ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);
	    
	    Button sortBtn = (Button) findViewById(R.id.btn_sortMethod);
	    Button newBtnn = (Button) findViewById(R.id.bnt_newNote);
	    sortBtn.getBackground().setColorFilter(0xFF0000FF, PorterDuff.Mode.MULTIPLY);
	    newBtnn.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
	    
	    adapter = new NoteAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);
	    
		ListView listView = (ListView) findViewById(R.id.notesList); 
		listView.setAdapter(adapter); 
		listView.setOnItemClickListener(this); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	protected void onSaveInstanceState(Bundle bundle) {
		  super.onSaveInstanceState(bundle);
		  bundle.putInt("sortingMethod", sortingMethod);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			new AlertDialog.Builder(this)
			.setTitle("About - Notes App")
			.setMessage("CSCI4100U - Assignment 2\n\nBy: Allen Kaganovsky\nSID: 100389429")
			.setPositiveButton("Close", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.show();
		break;
		case R.id.action_newNote:
			newNote(null);
		break;
		case R.id.action_sorting:
            notesSortingMethod(null);
			break;
		case R.id.deleteall:
			new AlertDialog.Builder(this)
			.setTitle("Delete All Notes?")
			.setMessage("Are you sure you want to delete ALL notes?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int whichButton) {
			    	confirmDeleteAll();
			    }})
			 .setNegativeButton("CANCEL", null).show();
			break;
		case R.id.action_quit:
			finish();
			break;
		}
		return true;
	}
	
	public void confirmDeleteAll() {
		DBControl dbc = new DBControl(this);
    	dbc.deleteAllNotes();
    	
    	List<Note> notes = dbc.getAllNotes(0);
    	
    	if (notes.size() == 0) {
	    	Note sample = new Note("Welcome to my App!", "This is a placeholder note!\n\nTo delete it, create a new note - THEN delete this note\n\nThis note will re-appear when the notes list is empty.");
	    	dbc.addNote(sample);
	    	notes.add(sample);
	    }
    	
    	
    	
    	adapter.clear();
    	
    	notes = dbc.getAllNotes(0);
    	
    	adapter.addAll(notes);
    	
    	adapter.notifyDataSetChanged();
    	
		dbc.close();
	}
	
	public void notesSortingMethod(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notes Sorting Method");
        builder.setSingleChoiceItems(R.array.sorting, sortingMethod, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
           
            
            switch(item)
            {
                case 0:
                	sortingMethod = 0;
                         break;
                case 1:
                	sortingMethod = 1;
                        
                        break;
                case 2:
                	sortingMethod = 2;
                        break;
                case 3:
                	sortingMethod = 3;           
                        break;
                
            }
            sortingDialog.dismiss();
            notes = dbc.getAllNotes(sortingMethod);
            adapter.clear();
            adapter.addAll(notes);
            }
        });
        sortingDialog = builder.create();
        sortingDialog.show();
	}
	
	public void newNote(View view) {
		Intent intent = new Intent(this, NewNote.class);
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putInt("sortingMethod", sortingMethod);
	    editor.commit();
	    startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView parent, View v, int position, long id) { 
		System.out.println(notes.get(position).getId());
		Intent intent = new Intent(this, EditNote.class);
		intent.putExtra("noteID", notes.get(position).getId());
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putInt("sortingMethod", sortingMethod);
	    editor.commit();
	    startActivity(intent);
	}

}
