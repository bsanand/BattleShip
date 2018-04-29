package com.sab.battleship;

public class Ship {

	public enum Orientation {
		VERTICAL, HORIZONTAL
	}

	private int id;
	private Position location;
	private Orientation direction;
	private int strikeCnt = 0;
	private int size;
	private boolean[] status; // TODO - can be changed to a bit array

	public Ship(int size, Position loc, Orientation dir) throws Exception {
		validateDimensions(size, loc, dir);
		this.size = size;
		this.location = loc;
		this.direction = dir;
		this.status = new boolean[size];

	}

	/*
	 * validate that given dimensions are within the bounds of the board
	 */
	private void validateDimensions(int size, Position loc, Orientation dir) throws Exception {
		switch (dir) {
		case HORIZONTAL:
			if (loc.getColumn() + size > Board.BOARD_COL_LENGTH)
				throw new Exception(String.format("Invalid ship size %d at location %s", size, loc));
			break;
		case VERTICAL:
			if (loc.getRow() + size > Board.BOARD_ROW_LENGTH)
				throw new Exception(String.format("Invalid ship size %d at location %s", size, loc));
			break;
		default:
			throw new Exception("Invalid orientation of ship " + dir);
		}
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
		//given position is on the ship based on checks in board.strike()
		StrikeResult result = StrikeResult.HIT;
		switch(direction) {
		case HORIZONTAL:
			idx = pos.getColumn() - location.getColumn();
			break;
		case VERTICAL:
		default: //not feasible, since the direction is a validated value
			idx = pos.getRow() - location.getRow();
			break;
		}
		if (!status[idx]) {
			status[idx] = true;
			strikeCnt++;
			if (strikeCnt == status.length)
				result = StrikeResult.SUNK;
		}
		return result;
	}

}
