package com.sab.battleship;

public class Ship {
	
	public enum Orientation {
		VERTICAL, HORIZONTAL
	}

	private int id;
	private Position location;
	private Orientation direction;
	private int strikeCnt=0;
	private int size;
	private boolean[] status; //TODO - can be changed to a bit array
	
	
	public Ship(int size, Position loc, Orientation dir) {
		
		this.size=size;
		this.location = loc;
		this.direction = dir;
		this.status = new boolean[size];
	}

	public Orientation getDirection() {
		return direction;
	}

	public Position getLocation() {
		return location;
	}

	public int getSize() {
		return size;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public StrikeResult strike(Position pos) {
		int idx;
		StrikeResult result = StrikeResult.HIT;
		if (pos.getRow() == location.getRow()) {
			idx = Math.abs(pos.getColumn()-location.getColumn());
		} else {
			idx = Math.abs(pos.getRow()-location.getRow());
		}
		if (!status[idx]) {
			status[idx]=true;
			strikeCnt++;
			if (strikeCnt==status.length)
				result = StrikeResult.SUNK;
		}
		return result;
	}



	

}
