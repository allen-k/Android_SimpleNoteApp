package ca.uoit.AllenKaganovsky.mobile_a2;

import java.util.Calendar;

public class Note {
	private int id;
	private String title;
	private String content;
	private String date;
	
	public Note(){}
	
	public Note(String title, String content) {
		super();
		this.title = title;
		this.content = content;
		this.date = String.valueOf(System.currentTimeMillis());
	}
	
	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
}
