package ca.uoit.AllenKaganovsky.mobile_a2;

import java.util.Calendar;
import java.util.List;

import android.os.*;
import android.app.*;
import android.content.*;
import android.graphics.PorterDuff;
import android.view.*;
import android.widget.*;
import android.support.v4.app.NavUtils;

public class EditNote extends Activity {

	Note note;
	int id;
	EditText title;
	EditText content;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		
		Button newBtn = (Button) findViewById(R.id.btn_new);
	    Button delBtn = (Button) findViewById(R.id.btn_del);
	    delBtn.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
	    newBtn.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			NavUtils.navigateUpFromSameTask(this);
		} else {
			id = extras.getInt("noteID");
		}
		
		DBControl dbc = new DBControl(this);
		
		note = dbc.getNote(id);
		
		dbc.close();
		
		title = (EditText) findViewById(R.id.edit_title);
		content = (EditText) findViewById(R.id.edit_content);
		TextView date = (TextView) findViewById(R.id.textDate);
		
		
		title.setText(note.getTitle());
		content.setText(note.getContent());
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.parseLong(note.getDate()));
		date.setText(c.getTime().toString());
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			DBControl dbc = new DBControl(this);
			
			dbc.updateNote(note, title.getText().toString(), content.getText().toString());
			dbc.close();
			
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void deleteNote(View view) {
		new AlertDialog.Builder(this)
		.setTitle("Delete Note?")
		.setMessage("Are you sure you want to delete this note?")
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int whichButton) {
		    	confirmDelete();
		    }})
		 .setNegativeButton("CANCEL", null).show();
	}
	
	public void confirmDelete() {
		DBControl dbc = new DBControl(this);
    	dbc.deleteNote(note);
    	
    	List<Note> notes = dbc.getAllNotes(0);
    	
    	if (notes.size() == 0) {
	    	Note sample = new Note("Welcome to my App!", "This is a placeholder note!\n\nTo delete it, create a new note - THEN delete this note\n\nThis note will re-appear when the notes list is empty.");
	    	dbc.addNote(sample);
	    	notes.add(sample);
	    }
    	
		dbc.close();
		NavUtils.navigateUpFromSameTask(this);
	}
	
	public void newNote(View view) {
		Intent intent = new Intent(this, NewNote.class);
	    startActivity(intent);
	}
	
}