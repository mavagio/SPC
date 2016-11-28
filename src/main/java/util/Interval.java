package main.java.util;

public class Interval {
	
	private String start;
	private String end;	
	
	
	public Interval(String start, String end) {
		this.start = start;
		this.end = end;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
