package ca.uoit.AllenKaganovsky.mobile_a2;

import java.util.Calendar;
import java.util.List;

import android.content.*;
import android.view.*;
import android.widget.*;

public class NoteAdapter<T> extends ArrayAdapter<Note> {

	private List<Note> notes;
	
	public NoteAdapter(Context context, int textViewResourceId,
			List<Note> objects) {
		super(context, textViewResourceId, objects);
		this.notes = objects;
	}
	
	public View getView(int position, View view, ViewGroup parent) {
		View v = view;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.notes_list_view, null);
		}
		
		Note n = notes.get(position);
		if (n != null) {
			TextView title = (TextView) v.findViewById(R.id.textDate);
			TextView date = (TextView) v.findViewById(R.id.textView2);
			title.setText(n.getTitle());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(Long.parseLong(n.getDate()));
			date.setText(c.getTime().toString());
		}
		
		return v;
	}
	
	

}
