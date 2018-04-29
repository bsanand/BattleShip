package com.sab.battleship;

public class Position {
	//row Id in the form of a, b, c, etc.
	char row; 
	
	//column Id in the form of 1, 2, 3, etc
	int col;
	
	private Position(char row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row-'a';
	}

	public int getColumn() {
		return col-1;
	}

	public static Position valueOf(String str) throws Exception {
		char row = str.charAt(0);
		int col = Integer.valueOf(str.substring(1));
		if (row<'a' || row>'a'+Board.BOARD_ROW_LENGTH 
				|| col<1 || col>Board.BOARD_COL_LENGTH)
			throw new Exception("Invalid position - "+str);
		return new Position(row, col);
	}
	
	@Override
	public String toString() {
		return String.format("%s%d", row, col);
	}
}
