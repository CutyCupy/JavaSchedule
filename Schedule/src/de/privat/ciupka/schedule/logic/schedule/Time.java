package de.privat.ciupka.schedule.logic.schedule;

public class Time {

	private int hour;
	private int minute;
	
	public boolean setTime(int hour, int minute) {
		if(0 <= hour && hour <= 23) {
			if(0 <= minute && minute <= 59) {
				this.hour = hour;
				this.minute = minute;
				return true;
			}
		}
		return false;
	}
	
	public boolean setTime(int time) {
		if(time >= 0) {
			this.hour = time / 60;
			this.minute = time % 60;
			return true;
		}
		return false;
	} 
	
	public int getTime() {
		return hour*60 + minute;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMinute() {
		return this.minute;
	}
	
	public boolean increaseTime(int hours, int minutes) {
		if (hours >= 0 && minutes >= 0) {
			this.hour = (this.hour + hours) % 24;
			this.minute = (this.minute + minutes) % 60;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String tempHour = hour < 10 ?  0 + String.valueOf(this.hour) : String.valueOf(this.hour);
		String tempMinute = minute < 10 ?  0 + String.valueOf(this.minute) : String.valueOf(this.minute);
		return tempHour + ":" + tempMinute;
	}
}
