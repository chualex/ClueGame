package clueGame;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	public Solution(String person, String weapon, String room) {
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getWeapon() {
		return weapon;
	}
	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public boolean compareTo(Solution a) {
		if (a.getPerson().equalsIgnoreCase(this.getPerson()) && a.getRoom().equalsIgnoreCase(this.getRoom()) && a.getWeapon().equalsIgnoreCase(this.getWeapon())) {
			return true;
		}
		else {
		return false;
		}
	}

}
