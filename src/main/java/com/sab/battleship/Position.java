package com.sab.battleship;

public class Position {
	//row Id in the form of a, b, c, etc.
	char row; 
	
	//column Id in the form of 1, 2, 3, etc
	int col;
	
	public Position(char row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row-'a';
	}

	public int getColumn() {
		return col-1;
	}

	public boolean isValid() {
		// TODO validate that the row and column are valid
		return true;
	}
}
