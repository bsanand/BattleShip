package com.sab.battleship;

import com.sab.battleship.Ship.Orientation;

public class Board {
	public static final int BOARD_ROW_LENGTH = 10;
	public static final int BOARD_COL_LENGTH = 10;
	public static final int SHIP_COUNT = 17;
	private static final int EMPTY_SPOT_ID = -1;

	//state of the board. Each position is an integer indicating the ship at that position
	int[][] board ;
	
	//ships on this board
	Ship[] ships ;
	
	int shipCount=0;
	
	public Board() {
		board = new int[BOARD_ROW_LENGTH][BOARD_COL_LENGTH];
		ships = new Ship[SHIP_COUNT];
		initBoard();
	}
	
	private void initBoard() {
		for (int i = 0;i<BOARD_ROW_LENGTH;i++)
			for (int j=0;j<BOARD_COL_LENGTH;j++)
				board[i][j]=EMPTY_SPOT_ID;
	}

	public void addShip(Ship ship) {
		ship.setId(shipCount);
		ships[shipCount] = ship;
		markPositions(ship);
		shipCount++;
	}
	
	private void markPositions(Ship ship) {
		if (ship.getDirection()==Orientation.HORIZONTAL) {
			for (int i=ship.getLocation().getColumn(); i< ship.getSize(); i++)
				board[ship.getLocation().getRow()][i]=ship.getId();
		} else {
			for (int i=ship.getLocation().getRow(); i< ship.getSize(); i++)
				board[ship.getLocation().getColumn()][i]=ship.getId();
		}
	}

	public StrikeResult strike(Position pos) throws Exception {
		StrikeResult result;
		if (!pos.isValid()) 
			throw new Exception(String.format("Invalid position - %s, %d", pos.getRow(), pos.getColumn()));
		
		int shipId = board[pos.getRow()][pos.getColumn()];
		if (shipId == EMPTY_SPOT_ID)
			result = StrikeResult.MISS;
		else {
			Ship ship = ships[shipId];
			result = ship.strike(pos);
		}
		return result;
	}

	
}
