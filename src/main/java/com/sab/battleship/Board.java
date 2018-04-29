package com.sab.battleship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {
	public static final int BOARD_ROW_LENGTH = 10;
	public static final int BOARD_COL_LENGTH = 10;
	public static final int MAX_SHIP_COUNT = 17; // no. of ships in the game(based on wiki)
	private static final int EMPTY_SPOT_ID = -1;

	private static final Logger logger = LoggerFactory.getLogger(Board.class);
	
	// state of the board. Each position is an integer indicating the ship at
	// that position
	private int[][] board;

	// ships on this board
	private Ship[] ships;

	//no. of ships on the board
	private int shipCount = 0;

	// no. of ships sunk on the board
	private int sunkShipCount = 0;

	public Board() {
		board = new int[BOARD_ROW_LENGTH][BOARD_COL_LENGTH];
		ships = new Ship[MAX_SHIP_COUNT];
		initBoard();
	}

	private void initBoard() {
		for (int i = 0; i < BOARD_ROW_LENGTH; i++)
			for (int j = 0; j < BOARD_COL_LENGTH; j++)
				board[i][j] = EMPTY_SPOT_ID;
	}

	/*
	 * Add given ship to the board. Mark positions on the board corresponding to the ship
	 * with id of the ship.
	 */
	public void addShip(Ship ship) throws Exception {
		logger.info("Adding ship of size {} to the board at location {}", ship.getSize(), ship.getLocation());
		if (shipCount == MAX_SHIP_COUNT)
			throw new Exception("No more ships can be added to the board");
		ship.setId(shipCount);
		logger.info("ID of the ship at location {}: {}", ship.getLocation(), ship.getId());
		ships[shipCount] = ship;
		markPositions(ship);
		shipCount++;
	}

	private void markPositions(Ship ship) throws Exception {
		logger.info("Marking positions on board for ship {}", ship.getId());
		switch (ship.getDirection()) {
		case HORIZONTAL:
			markHorizontalPositions(ship);
			break;
		case VERTICAL:
			markVerticalPositions(ship);
			break;
		default:
			throw new Exception("Invalid orientation of ship " + ship.getDirection());
		}
	}

	private void markVerticalPositions(Ship ship) throws Exception {
		for (int i = ship.getLocation().getRow(); i < ship.getLocation().getRow() + ship.getSize(); i++) {
			if (board[i][ship.getLocation().getColumn()] == EMPTY_SPOT_ID)
				board[i][ship.getLocation().getColumn()] = ship.getId();
			else {
				logger.error("Ship {} (location - {}, size - {}) overlaps with ship {}",
						ship.getId(), ship.getLocation(), ship.getSize(), board[i][ship.getLocation().getColumn()] );
				// undo changes on the board so far in the loop since 
				// this ship intersects with an existing ship
				for (int j = i - 1; j >= ship.getLocation().getRow(); j--)
					board[j][ship.getLocation().getColumn()] = EMPTY_SPOT_ID;
				throw new Exception(String.format("Ship(Location - %s, size - %d) overlaps with an existing ship",
						ship.getLocation(), ship.getSize()));
			}
		}
	}

	private void markHorizontalPositions(Ship ship) throws Exception {
		for (int i = ship.getLocation().getColumn(); i < ship.getLocation().getColumn() + ship.getSize(); i++) {
			if (board[ship.getLocation().getRow()][i] == EMPTY_SPOT_ID)
				board[ship.getLocation().getRow()][i] = ship.getId();
			else {
				logger.error("Ship {} (location - {}, size - {}) overlaps with ship {}",
						ship.getId(), ship.getLocation(), ship.getSize(), board[ship.getLocation().getRow()][i] );
				// undo changes on the board so far in the loop since 
				// this ship intersects with an existing ship
				for (int j = i - 1; j >= ship.getLocation().getColumn(); j--)
					board[ship.getLocation().getRow()][j] = EMPTY_SPOT_ID;
				throw new Exception(String.format("Ship(Location - %s, size - %d) overlaps with an existing ship",
						ship.getLocation(), ship.getSize()));
			}
		}
	}

	/*
	 * Strike the board at given position. If there is no ship at this position, return MISS
	 * If there is a ship at this position, strike the ship at this position. If all the
	 * blocks in the ship have been struck, return SUNK. If all the ships have been SUNK,
	 * return WIN.
	 */
	public StrikeResult strike(Position pos) throws Exception {
		StrikeResult result;
		logger.info("Striking on the board at position {}", pos);
		int shipId = board[pos.getRow()][pos.getColumn()];
		if (shipId == EMPTY_SPOT_ID) {
			result = StrikeResult.MISS;
			logger.info("Strike resulted in MISS");
		} else {
			logger.info("Strike hit ship {}", shipId);
			Ship ship = ships[shipId];
			result = ship.strike(pos);
			if (result == StrikeResult.SUNK) {
				sunkShipCount++;
				logger.info("Ship {} has sunk", shipId);
			}
			if (sunkShipCount == shipCount) {
				result = StrikeResult.WIN;
				logger.info("All ships on the board have sunk");
			}
		}
		return result;
	}

}
