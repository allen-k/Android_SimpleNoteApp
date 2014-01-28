package ca.uoit.AllenKaganovsky.mobile_a2;

import android.os.*;
import android.app.*;
import android.content.*;
import android.graphics.PorterDuff;
import android.view.*;
import android.widget.*;
import android.support.v4.app.NavUtils;

public class NewNote extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		
	    Button save = (Button) findViewById(R.id.btn_new);
	    save.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
		
		
		// Show the Up button in the action bar.
		setupActionBar();
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
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void newNote(View view) {
		Intent intent = new Intent(this, NewNote.class);
		startActivity(intent);
	}
	
	public void addNote(View view) {
		DBControl dbc = new DBControl(this);
    	
		EditText title = (EditText) findViewById(R.id.edit_title);
		EditText content = (EditText) findViewById(R.id.edit_content);
		
		dbc.addNote(new Note(title.getText().toString(), content.getText().toString()));
		
		dbc.close();
		NavUtils.navigateUpFromSameTask(this);
	}

}
